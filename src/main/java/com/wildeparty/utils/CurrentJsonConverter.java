package com.wildeparty.utils;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wildeparty.model.Current;
import jakarta.persistence.AttributeConverter;

public class CurrentJsonConverter implements AttributeConverter<Current, String> {
  
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Current current) {
        try {
            return objectMapper.writeValueAsString(current);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not convert to Json", e);
        }
    }

    @Override
    public Current convertToEntityAttribute(String json) {
        try {
            return objectMapper.readValue(json, Current.class);
        } catch (IOException e) {
            throw new RuntimeException("Could not convert from Json", e);
        }
    }
}
