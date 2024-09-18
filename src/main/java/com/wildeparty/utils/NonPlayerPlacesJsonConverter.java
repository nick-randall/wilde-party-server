package com.wildeparty.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wildeparty.model.NonPlayerPlaces;

import jakarta.persistence.AttributeConverter;

public class NonPlayerPlacesJsonConverter implements AttributeConverter<NonPlayerPlaces, String> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(NonPlayerPlaces snapshot) {
        try {
            return objectMapper.writeValueAsString(snapshot);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not convert to Json", e);
        }
    }

    @Override
    public NonPlayerPlaces convertToEntityAttribute(String json) {
        try {
            return objectMapper.readValue(json, NonPlayerPlaces.class);
        } catch (IOException e) {
            throw new RuntimeException("Could not convert from Json", e);
        }
    }
}



