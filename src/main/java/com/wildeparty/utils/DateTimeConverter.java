package com.wildeparty.utils;


import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.time.LocalDateTime;


public class DateTimeConverter implements AttributeConverter<LocalDateTime, String> {
  private ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(LocalDateTime time) {
    try {
      return objectMapper.writeValueAsString(time);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Could not convert to Json", e);
    }
  }

  @Override
  public LocalDateTime convertToEntityAttribute(String json) {
    try {
      return objectMapper.readValue(json, new TypeReference<LocalDateTime>(){});
    } catch (IOException e) {
      throw new RuntimeException("Could not convert from Json", e);
    }
  }


  
}
