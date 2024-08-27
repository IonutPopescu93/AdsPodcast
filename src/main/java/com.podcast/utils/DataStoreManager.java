package com.podcast.utils;

import java.util.HashMap;
import java.util.Map;

public class DataStoreManager {
    private static Map<String, Object> dataStore = new HashMap<>();

    public DataStoreManager() {
        dataStore = new HashMap<>();
    }

    public static void addToDataStore(String key, Object value) {
        dataStore.put(key, value);
    }

    public static Object getFromDataStore(String key) {
        Object value = dataStore.get(key);
        return value;
    }

    public static boolean containsKey(String key) {
        return dataStore.containsKey(key);
    }
}
