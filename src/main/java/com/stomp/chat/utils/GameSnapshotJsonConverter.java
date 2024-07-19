package com.stomp.chat.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stomp.chat.model.GameSnapshot;

import jakarta.persistence.AttributeConverter;

  public class GameSnapshotJsonConverter implements AttributeConverter<GameSnapshot, String> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(GameSnapshot snapshot) {
        try {
            return objectMapper.writeValueAsString(snapshot);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not convert to Json", e);
        }
    }

    @Override
    public GameSnapshot convertToEntityAttribute(String json) {
        try {
            return objectMapper.readValue(json, GameSnapshot.class);
        } catch (IOException e) {
            throw new RuntimeException("Could not convert from Json", e);
        }
    }
}

