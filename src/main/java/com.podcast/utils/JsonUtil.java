package com.podcast.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    /**
     * This method populates an object of the provided class type with the data from the provided JSON string
     *
     * @param jsonString
     * @param object
     * @param <T>
     * @return
     * @throws JsonProcessingException
     */
    public static <T> T populateObjectFromJsonString(String jsonString, Class<T> object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, object);
    }
}
