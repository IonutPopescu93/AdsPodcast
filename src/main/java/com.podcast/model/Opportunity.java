package com.podcast.model;

import java.util.Map;

public class Opportunity {
    private Long originalEventTime;
    private int maxDuration;
    private Map<String, Zone> zones;
    private PositionUrlSegments positionUrlSegments;
    private int insertionRate;

    public Long getOriginalEventTime() {
        return originalEventTime;
    }

    public void setOriginalEventTime(Long originalEventTime) {
        this.originalEventTime = originalEventTime;
    }

    public int getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(int maxDuration) {
        this.maxDuration = maxDuration;
    }

    public Map<String, Zone> getZones() {
        return zones;
    }

    public void setZones(Map<String, Zone> zones) {
        this.zones = zones;
    }

    public PositionUrlSegments getPositionUrlSegments() {
        return positionUrlSegments;
    }

    public void setPositionUrlSegments(PositionUrlSegments positionUrlSegments) {
        this.positionUrlSegments = positionUrlSegments;
    }

    public int getInsertionRate() {
        return insertionRate;
    }

    public void setInsertionRate(int insertionRate) {
        this.insertionRate = insertionRate;
    }
}
