package com.wildeparty.model.DTO;

import com.fasterxml.jackson.annotation.JsonValue;

public enum InboundGameMessageType {
  GAME_SNAPSHOT("gameSnapshot");

  private String name;

  @JsonValue
  public String getName() {
    return this.name;
  }

  InboundGameMessageType(String name) {
    this.name = name;
  }
}
