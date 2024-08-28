package com.podcast.service;

import com.podcast.model.Opportunity;
import com.podcast.model.PodcastShow;
import com.podcast.utils.DataStoreManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.podcast.utils.JsonUtil.populateObjectFromJsonString;

public class PodcastService {


    /**
     * This method returns the list of PodcastShows from a text file
     *
     * @return
     * @throws IOException
     */
    public static List<PodcastShow> getPodcastsFromFile(String filePath) throws IOException {
        List<PodcastShow> podcastShowList = new ArrayList<>();

        //Get the file
        File downloadFile = new File(filePath);
        BufferedReader podcastFile = null;

        //Read the file if exists
        if (downloadFile.exists()) {
            try {
                podcastFile = new BufferedReader(new FileReader(downloadFile));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            throw new RuntimeException("File not found!");
        }

        //Parse the file and populate the object
        String line;
        while ((line = podcastFile.readLine()) != null) {
            PodcastShow download = populateObjectFromJsonString(line, PodcastShow.class);
            if (download != null) {
                podcastShowList.add(download);
            }
        }
        return podcastShowList;
    }

    /**
     * Filter and calculate the number of downloads based on desired location
     *
     * @param podcastShowList
     * @param city
     */
    public static void calculatedMostPopularShowInCity(List<PodcastShow> podcastShowList, String city) {
        Map<String, Integer> showCounts = new HashMap<>();

        //Filter the list based on desired location
        List<PodcastShow> podcastFromCity = podcastShowList.stream().filter(podcastShow -> podcastShow.getCity().equalsIgnoreCase(city)).toList();

        //Separate the shows and count the number of entries
        for (PodcastShow podcastShow : podcastFromCity) {
            String showId = podcastShow.getDownloadIdentifier().getShowId();
            showCounts.put(showId, showCounts.getOrDefault(showId, 0) + 1);
        }

        String mostViewedShow = Collections.max(showCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
        int numberOfViews = Collections.max(showCounts.entrySet(), Map.Entry.comparingByValue()).getValue();

        //Print the
        System.out.println("Most popular show is: " + mostViewedShow);
        System.out.println("Number of downloads is: " + numberOfViews);

        DataStoreManager.addToDataStore("mostViewedShow", mostViewedShow);
        DataStoreManager.addToDataStore("numberOfViews", numberOfViews);
    }



    /**
     * This method get the most used device and calculte the number of use
     *
     * @param podcastShowList
     */
    public static void calculateMostUsedDevice(List<PodcastShow> podcastShowList) {
        Map<String, Integer> deviceCounts = new HashMap<>();
        for (PodcastShow podcastShow : podcastShowList) {
            String deviceType = podcastShow.getDeviceType();
            deviceCounts.put(deviceType, deviceCounts.getOrDefault(deviceType, 0) + 1);
        }

        String mostUsedDevice = Collections.max(deviceCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
        int numberOfDownloads = Collections.max(deviceCounts.entrySet(), Map.Entry.comparingByValue()).getValue();

        System.out.println("Most popular device is: " + mostUsedDevice);
        System.out.println("Number of downloads is: " + numberOfDownloads);
        DataStoreManager.addToDataStore("mostUsedDevice", mostUsedDevice);
        DataStoreManager.addToDataStore("numberOfDownloads", numberOfDownloads);
    }


    public static void calculatePrerollOpportunities(List<PodcastShow> podcastShowList) {
        Map<String, Integer> prerollOpportunities = new HashMap<>();
        for (PodcastShow podcastShow : podcastShowList) {
            String showId = podcastShow.getDownloadIdentifier().getShowId();
            List<Opportunity> opportunities = podcastShow.getOpportunities();
            for (Opportunity opportunity : opportunities) {
                List<String> adBreakIndex = opportunity.getPositionUrlSegments().getAdBreakIndex();
                if (adBreakIndex.contains("preroll")) {
                    prerollOpportunities.put(showId, prerollOpportunities.getOrDefault(showId, 0) + 1);
                }
            }

        }
        prerollOpportunities.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(entry -> System.out.println("Show id: " + entry.getKey() + ", Preroll Opportunity Number: " + entry.getValue()));
        DataStoreManager.addToDataStore("prerollOpportunities", prerollOpportunities);
    }


    public static void calculateWeeklyShows(List<PodcastShow> podcastShowList) {
        Map<String, Set<Timestamp>> show = new HashMap<>();
        Map<String, String> weeklyShows = new HashMap<>();
        //Group timeStamps by showId
        for (PodcastShow podcastShow : podcastShowList) {
            String showId = podcastShow.getDownloadIdentifier().getShowId();

            Set<Timestamp> time = new TreeSet<>();

            List<PodcastShow> filteredShows = podcastShowList.stream()
                    .filter(podcast -> podcast.getDownloadIdentifier().getShowId().equals(showId))
                    .collect(Collectors.toList());

            for (PodcastShow filteredShow : filteredShows) {
                for (Opportunity opportunity : filteredShow.getOpportunities()) {
                    time.add(opportunity.getOriginalEventTime());
                }
                filteredShow.getOpportunities().get(0).getOriginalEventTime();
            }
            show.put(showId, time);
        }


        for (Map.Entry<String, Set<Timestamp>> showEntry : show.entrySet()) {
            String showId = showEntry.getKey();
            Set<Timestamp> timestamps = showEntry.getValue();

            if (isWeeklyShow(timestamps)) {
                Timestamp firstTimestamp = timestamps.iterator().next();
                SimpleDateFormat dayFormat = new SimpleDateFormat("E");
                dayFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                String dayOfTheWeeke = dayFormat.format(firstTimestamp);
                String timeOfDay = timeFormat.format(firstTimestamp);

                weeklyShows.put(showId, dayOfTheWeeke + " " + timeOfDay);
                System.out.println("Weekly show: " + showId + " - " + dayOfTheWeeke + " " + timeOfDay);
            }

            DataStoreManager.addToDataStore("weeklyShows", weeklyShows);
        }
    }

    private static boolean isWeeklyShow(Set<Timestamp> timestamps) {
        if (timestamps.size() < 2) {
            return false;
        }

        Iterator<Timestamp> timestampIterator = timestamps.iterator();
        Timestamp previous = timestampIterator.next();

        while (timestampIterator.hasNext()) {
            Timestamp currentTime = timestampIterator.next();
            long difference = currentTime.getTime() - previous.getTime();

            if (Math.abs(difference - 7L * 24 * 60 * 60 * 1000) > 60 * 1000) {
                return false;
            }
            previous = currentTime;
        }
        return true;
    }
}
