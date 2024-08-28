import com.podcast.model.PodcastShow;
import com.podcast.utils.DataStoreManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.podcast.service.PodcastService.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PodcastAnalyzerTest {
    private static List<PodcastShow> podcastAnalyzerTestList;

    @BeforeAll
    public static void setUp() throws IOException {
        podcastAnalyzerTestList = getPodcastsFromFile("src/resources/downloads.txt");
    }

    @Test
    public void validateTheMostPopularShow() {
        //Prepare data
        calculatedMostPopularShowInCity(podcastAnalyzerTestList, "san francisco");

        String expectedMostViewedShow = "Who Trolled Amber";
        String actualShow = (String) DataStoreManager.getFromDataStore("mostViewedShow");
        assertEquals(expectedMostViewedShow, actualShow);

        int expectedNumberOfViews = 24;
        int actualNumberOfviews = (int) DataStoreManager.getFromDataStore("numberOfViews");
        assertEquals(expectedNumberOfViews, actualNumberOfviews);
    }

    @Test
    public void validateMostUsedDevice() {
        //Prepare data
        calculateMostUsedDevice(podcastAnalyzerTestList);


        String expectedMostUsedDevice = "mobiles & tablets";
        String actualMostUsedDevice = (String) DataStoreManager.getFromDataStore("mostUsedDevice");
        assertEquals(expectedMostUsedDevice, actualMostUsedDevice);

        int expectedNumberOfDownloads = 60;
        int actualNumberOfDownloads = (int) DataStoreManager.getFromDataStore("numberOfDownloads");
        assertEquals(expectedNumberOfDownloads, actualNumberOfDownloads);
    }

    @Test
    public void validatePrerollOpportunities() {
        //Prepare data
        calculatePrerollOpportunities(podcastAnalyzerTestList);

        Map<String, Integer> expectedPrerollOpportunities = new HashMap<>();
        expectedPrerollOpportunities.put("Stuff You Should Know", 40);
        expectedPrerollOpportunities.put("Who Trolled Amber", 40);
        expectedPrerollOpportunities.put("Crime Junkie", 30);
        expectedPrerollOpportunities.put("The Joe Rogan Experience", 10);

        Map<String, String> actualPrerollOpportunities = (Map<String, String>) DataStoreManager.getFromDataStore("prerollOpportunities");

        assertEquals(expectedPrerollOpportunities, actualPrerollOpportunities);
    }

    @Test
    public void validateWeeklyShows() {
        //Prepare data
        calculateWeeklyShows(podcastAnalyzerTestList);

        Map<String, String> expectedWeeklyShows = new HashMap<>();
        expectedWeeklyShows.put("Crime Junkie", "Wed 22:00");
        expectedWeeklyShows.put("Who Trolled Amber", "Mon 20:00");

        Map<String, String> actualWeeklyShows = (Map<String, String>) DataStoreManager.getFromDataStore("weeklyShows");
        assertEquals(expectedWeeklyShows, actualWeeklyShows);
    }
}
