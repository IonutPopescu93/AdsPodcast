package com.podcast.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PositionUrlSegments {
    @JsonProperty("aw_0_ais.adBreakIndex")
    private List<String> adBreakIndex;
    @JsonProperty("aw_0_ais.nextEventMs")
    private List<String> nextEventMs;

    public List<String> getAdBreakIndex() {
        return adBreakIndex;
    }

    public void setAdBreakIndex(List<String> adBreakIndex) {
        this.adBreakIndex = adBreakIndex;
    }

    public List<String> getNextEventMs() {
        return nextEventMs;
    }

    public void setNextEventMs(List<String> nextEventMs) {
        this.nextEventMs = nextEventMs;
    }
}
