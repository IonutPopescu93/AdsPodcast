package com.podcast.service;

import com.podcast.utils.DataStoreManager;
import org.assertj.core.api.SoftAssertions;

import java.util.Map;

public class PodcastValidation {


    public static void validateTheMostPopularShow(String expectedShowResponse, int expectedNumberOfEntries) {
        if (!DataStoreManager.containsKey("mostViewedShow") && !DataStoreManager.containsKey("numberOfViews")) {
            throw new RuntimeException("Desired object to validate was not found in DataStore");
        }
        validate(DataStoreManager.getFromDataStore("mostViewedShow"), expectedShowResponse);
        validate(DataStoreManager.getFromDataStore("numberOfViews"), expectedNumberOfEntries);
    }

    public static void validateMostUsedDevice(String expectedDevice, int numberOfDownloads) {
        if (!DataStoreManager.containsKey("mostUsedDevice") && !DataStoreManager.containsKey("numberOfDownloads")) {
            throw new RuntimeException("Desired object to validate was not found in DataStore");
        }
        validate(DataStoreManager.getFromDataStore("mostUsedDevice"), expectedDevice);
        validate(DataStoreManager.getFromDataStore("numberOfDownloads"), numberOfDownloads);
    }


    public static void validatePrerollOpportunities(Map<String, Integer> expectedPrerollOpportunities) {
        if (!DataStoreManager.containsKey("prerollOpportunities")) {
            throw new RuntimeException("Desired object to validate was not found in DataStore");
        }
        validate(DataStoreManager.getFromDataStore("prerollOpportunities"), expectedPrerollOpportunities);
    }


    public static void validateWeeklyShows(Map<String, String> weeklyShows) {
        if (!DataStoreManager.containsKey("weeklyShows")) {
            throw new RuntimeException("Desired object to validate was not found in DataStore");
        }
        validate(DataStoreManager.getFromDataStore("weeklyShows"), weeklyShows);
    }

    private static void validate(Object actualResponse, Object expectedResponse) {
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(actualResponse).isEqualTo(expectedResponse);
        softAssertions.assertAll();

        System.out.println("The validation was successfully! The actual response: " + actualResponse.toString()
                + " matched the expected response: " + expectedResponse.toString());
    }
}
