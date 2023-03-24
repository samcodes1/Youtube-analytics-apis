package com.rtechnologies.youtubetool.Model;

import java.time.LocalDateTime;
import java.util.List;

public class Video {
    private String id;
    private String title;
    private String description;
    private LocalDateTime publishedAt;

    private List<String> keywords;
    private String thumbnailLink;
    private long likes;
    private long comments;
    private long views;

    public Video() {
    }

    public Video(String id, String title, String description, LocalDateTime publishedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.publishedAt = publishedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getThumbnailLink() {
        return thumbnailLink;
    }

    public void setThumbnailLink(String thumbnailLink) {
        this.thumbnailLink = thumbnailLink;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public long getComments() {
        return comments;
    }

    public void setComments(long comments) {
        this.comments = comments;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }
}

