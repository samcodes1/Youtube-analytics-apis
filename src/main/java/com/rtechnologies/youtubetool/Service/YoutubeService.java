package com.rtechnologies.youtubetool.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.*;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeRequestInitializer;
import com.google.api.services.youtube.YouTubeScopes;
import com.rtechnologies.youtubetool.Model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class YoutubeService {
    private static final String YOUTUBE_API_URL = "https://www.googleapis.com/youtube/v3/search?key={key}&channelId={channelId}&part=snippet,id&order=date&maxResults={maxResults}";
    private final RestTemplate restTemplate;

    private String YOUTUBE_API_KEY = "AIzaSyBCQpIJxAt9BXv6KQivUzyTDlQbCsiAGII";

    public YoutubeService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    //Function that gets simple video details
    public List<Video> getChannelVideos(String apiKey, String channelId, int maxResults) {
        String url = YOUTUBE_API_URL.replace("{key}", apiKey).replace("{channelId}", channelId).replace("{maxResults}", String.valueOf(maxResults));
        ResponseEntity<YouTubeApiResponse> responseEntity = restTemplate.getForEntity(url, YouTubeApiResponse.class);
        List<Video> videos = new ArrayList<>();
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            YouTubeApiResponse apiResponse = responseEntity.getBody();
            if (apiResponse != null && apiResponse.getItems() != null) {
                for (YouTubeApiResponse.Item item : apiResponse.getItems()) {
                    Video video = new Video(item.getId().getVideoId(), item.getSnippet().getTitle(), item.getSnippet().getDescription(), item.getSnippet().getPublishedAt());
                    try {
                        videos.add(getDetailedVideoData(video));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return videos;
    }

    //Function to get detail of every video
    public Video getDetailedVideoData(Video videoObj) throws JsonProcessingException {
        final String apiURL = "https://www.googleapis.com/youtube/v3/videos?part=snippet,statistics&id=" + videoObj.getId() + "&key=+" + YOUTUBE_API_KEY;
        String response = restTemplate.getForObject(apiURL, String.class);
        try {
            JSONObject json = new JSONObject(response);

            System.out.println(json.toString());
            JSONArray items = json.getJSONArray("items");
            JSONObject item = items.getJSONObject(0);

            JSONObject snippet = item.getJSONObject("snippet");
            String title = snippet.getString("title");
            String description = snippet.getString("description");
            String channelId = snippet.getString("channelId");
            String channelTitle = snippet.getString("channelTitle");

            JSONObject thumbnails = snippet.getJSONObject("thumbnails");
            JSONObject defaultThumbnail = thumbnails.getJSONObject("default");
            String thumbnailUrl = defaultThumbnail.getString("url");

            JSONArray tags = snippet.getJSONArray("tags");
            List<String> tagList = new ArrayList<>();
            for (int i = 0; i < tags.length(); i++) {
                tagList.add(tags.getString(i));
            }

            JSONObject statistics = item.getJSONObject("statistics");
            Long likeCount = statistics.getLong("likeCount");
            Long commentCount = statistics.getLong("commentCount");
            Long viewsCount = statistics.getLong("viewCount");

            videoObj.setKeywords(tagList);
            videoObj.setComments(commentCount);
            videoObj.setLikes(likeCount);
            videoObj.setThumbnailLink(thumbnailUrl);
            videoObj.setViews(viewsCount);
        } catch (Exception e) {
            System.out.println("There is some issue in response");
        }
        return videoObj;
    }

    public Channel getChannelDetails(String channelId) throws Exception {
        final String apiURL = "https://www.googleapis.com/youtube/v3/channels?part=snippet,statistics&id=" + channelId + "&key=" + YOUTUBE_API_KEY;
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(apiURL, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response);
        JsonNode channelNode = rootNode.get("items").get(0);

        String channelName = channelNode.get("snippet").get("title").asText();
        String profileImageUrl = channelNode.get("snippet").get("thumbnails").get("medium").get("url").asText();
        int totalSubscribers = channelNode.get("statistics").get("subscriberCount").asInt();
        int totalVideos = channelNode.get("statistics").get("videoCount").asInt();
        int totalViews = channelNode.get("statistics").get("viewCount").asInt();


        Channel channel = new Channel(channelId, channelName, profileImageUrl,
                totalSubscribers, totalVideos, 0, totalViews);
        TempData.channel = channel;
        return channel;
        // Do something with the extracted fields
        // For example, store them in an object or print them out
    }

    public VideoStats averageStats(List<Video> videos){
        long totalViews = 0;
        long totalComments = 0;
        long totalLikes = 0;

        for (Video video : videos) {
            totalViews += video.getViews();
            totalComments += video.getComments();
            totalLikes += video.getLikes();
        }

        long avgViews = (long) totalViews / videos.size();
        long avgComments = (long) totalComments / videos.size();
        long avgLikes = (long) totalLikes / videos.size();

        VideoStats stats = new VideoStats(avgViews,avgComments,avgLikes);

        return stats;
    }
}

