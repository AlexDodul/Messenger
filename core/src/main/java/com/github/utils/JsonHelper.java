package com.github.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class JsonHelper {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static Optional<String> toJson(Object obj) {
        try {
            return Optional.of(MAPPER.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            System.out.printf("Enter %s?\n",e.getMessage());
        }
        return Optional.empty();
    }

    public static <T>Optional <T> fromJson(String str,Class<T> clz){
        try {
            return Optional.of(MAPPER.readValue(str,clz));
        } catch (JsonProcessingException e) {
            System.out.printf("Enter %s?\n",e.getMessage());
        }
        return Optional.empty();
    }
}
