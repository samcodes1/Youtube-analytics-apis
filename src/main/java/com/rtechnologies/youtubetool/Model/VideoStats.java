package com.rtechnologies.youtubetool.Model;

public class VideoStats {
    private long avgViews;
    private long avgComments;
    private long avgLikes;

    public VideoStats() {
    }

    public VideoStats(long avgViews, long avgComments, long avgLikes) {
        this.avgViews = avgViews;
        this.avgComments = avgComments;
        this.avgLikes = avgLikes;
    }

    public long getAvgViews() {
        return avgViews;
    }

    public void setAvgViews(long avgViews) {
        this.avgViews = avgViews;
    }

    public long getAvgComments() {
        return avgComments;
    }

    public void setAvgComments(long avgComments) {
        this.avgComments = avgComments;
    }

    public long getAvgLikes() {
        return avgLikes;
    }

    public void setAvgLikes(long avgLikes) {
        this.avgLikes = avgLikes;
    }
}
