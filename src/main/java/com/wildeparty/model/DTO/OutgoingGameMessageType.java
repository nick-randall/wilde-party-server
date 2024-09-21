package com.wildeparty.model.DTO;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OutgoingGameMessageType {
  JOIN("join"),
  LEAVE("leave"),
  ERROR("error"),
  GAME_SNAPSHOTS("game_snapshots");

  private String name;

  @JsonValue
  public String getName() {
    return name;
  }

  OutgoingGameMessageType(String name) {
    this.name = name;
  }

}
