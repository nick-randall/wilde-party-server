package com.wildeparty.utils;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wildeparty.model.cards.CardActionResult;
import com.wildeparty.model.gameElements.Player;

import jakarta.persistence.AttributeConverter;

public class CardActionResultsMapJsonConverter implements AttributeConverter<Map<Integer, CardActionResult>, String> {
      private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<Integer, CardActionResult> cardActionResultsMap) {
        try {
            return objectMapper.writeValueAsString(cardActionResultsMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not convert to Json", e);
        }
    }

    @Override
    public Map<Integer, CardActionResult> convertToEntityAttribute(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<Map<Integer, CardActionResult>>(){});
        } catch (IOException e) {
            throw new RuntimeException("Could not convert from Json", e);
        }
    }
}
