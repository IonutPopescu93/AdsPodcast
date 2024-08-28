package com.podcast.model;

import java.sql.Timestamp;
import java.util.Map;

public class Opportunity {
    private Timestamp originalEventTime;
    private int maxDuration;
    private Map<String, Zone> zones;
    private PositionUrlSegments positionUrlSegments;
    private int insertionRate;

    public Timestamp getOriginalEventTime() {
        return originalEventTime;
    }

    public void setOriginalEventTime(Timestamp originalEventTime) {
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
