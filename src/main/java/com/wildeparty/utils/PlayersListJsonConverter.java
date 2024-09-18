package com.wildeparty.utils;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wildeparty.model.Player;

import jakarta.persistence.AttributeConverter;

public  class PlayersListJsonConverter implements AttributeConverter<List<Player>, String> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Player> snapshot) {
        try {
            return objectMapper.writeValueAsString(snapshot);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not convert to Json", e);
        }
    }

    @Override
    public List<Player> convertToEntityAttribute(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<Player>>(){});
        } catch (IOException e) {
            throw new RuntimeException("Could not convert from Json", e);
        }
    }
}





