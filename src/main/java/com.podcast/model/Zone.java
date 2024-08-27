package com.podcast.model;

public class Zone {
    private int id;
    private int maxAds;
    private int maxDuration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaxAds() {
        return maxAds;
    }

    public void setMaxAds(int maxAds) {
        this.maxAds = maxAds;
    }

    public int getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(int maxDuration) {
        this.maxDuration = maxDuration;
    }
}
