package com.rtechnologies.youtubetool.Model;

public class Channel {
    private String id;
    private String channelName;
    private String profileImageUrl;
    private int totalSubscribers;
    private int totalVideos;
    private int totalComments;
    private int totalViews;

    public Channel() {
    }

    public Channel(String id, String channelName, String profileImageUrl,
                   int totalSubscribers, int totalVideos, int totalComments, int totalViews) {
        this.id = id;
        this.channelName = channelName;
        this.profileImageUrl = profileImageUrl;
        this.totalSubscribers = totalSubscribers;
        this.totalVideos = totalVideos;
        this.totalComments = totalComments;
        this.totalViews = totalViews;
    }

    public Channel(String channelName, String profileImageUrl, int totalSubscribers,
                   int totalVideos, int totalComments, int totalViews) {
        this.channelName = channelName;
        this.profileImageUrl = profileImageUrl;
        this.totalSubscribers = totalSubscribers;
        this.totalVideos = totalVideos;
        this.totalComments = totalComments;
        this.totalViews = totalViews;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public int getTotalSubscribers() {
        return totalSubscribers;
    }

    public void setTotalSubscribers(int totalSubscribers) {
        this.totalSubscribers = totalSubscribers;
    }

    public int getTotalVideos() {
        return totalVideos;
    }

    public void setTotalVideos(int totalVideos) {
        this.totalVideos = totalVideos;
    }

    public int getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(int totalComments) {
        this.totalComments = totalComments;
    }

    public int getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(int totalViews) {
        this.totalViews = totalViews;
    }
}

