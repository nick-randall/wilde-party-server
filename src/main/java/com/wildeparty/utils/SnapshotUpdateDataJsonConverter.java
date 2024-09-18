package com.wildeparty.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wildeparty.model.SnapshotUpdateData;

import jakarta.persistence.AttributeConverter;

public class SnapshotUpdateDataJsonConverter implements AttributeConverter<SnapshotUpdateData, String> {
      private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(SnapshotUpdateData current) {
        try {
            return objectMapper.writeValueAsString(current);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not convert to Json", e);
        }
    }

    @Override
    public SnapshotUpdateData convertToEntityAttribute(String json) {
        try {
            return objectMapper.readValue(json, SnapshotUpdateData.class);
        } catch (IOException e) {
            throw new RuntimeException("Could not convert from Json", e);
        }
    }
}
