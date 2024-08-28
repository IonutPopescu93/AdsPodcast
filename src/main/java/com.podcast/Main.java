package com.podcast;

import com.podcast.model.PodcastShow;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.podcast.service.PodcastService.*;
import static com.podcast.service.PodcastValidation.*;

public class Main {
    public static void main(String[] args) throws IOException {
        //Get the podcastShowList from the file
        List<PodcastShow> podcastShowList = getPodcastsFromFile("src/resources/downloads.txt");

        //Calculate and validate the most popular show in specified city
        calculatedMostPopularShowInCity(podcastShowList, "san francisco");
        validateTheMostPopularShow("Who Trolled Amber", 24);

        //Calculate the most used device to lessen
        calculateMostUsedDevice(podcastShowList);
        validateMostUsedDevice("mobiles & tablets", 60);

        //Calculate the number of preroll opportunities
        calculatePrerollOpportunities(podcastShowList);
        Map<String, Integer> expectedPrerollOpportunities = new HashMap<>();
        expectedPrerollOpportunities.put("Stuff You Should Know", 40);
        expectedPrerollOpportunities.put("Who Trolled Amber", 40);
        expectedPrerollOpportunities.put("Crime Junkie", 30);
        expectedPrerollOpportunities.put("The Joe Rogan Experience", 10);
        validatePrerollOpportunities(expectedPrerollOpportunities);

        //Calculate weekly podcasts
        calculateWeeklyShows(podcastShowList);
        Map<String, String> weeklyShows = new HashMap<>();
        weeklyShows.put("Crime Junkie", "Wed 22:00");
        weeklyShows.put("Who Trolled Amber", "Mon 20:00");
        validateWeeklyShows(weeklyShows);
    }



}