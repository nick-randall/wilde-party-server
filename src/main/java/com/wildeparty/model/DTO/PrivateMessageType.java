package com.wildeparty.model.DTO;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PrivateMessageType {
  INVITE("invite"),
  NOT_IN_GAME_ERROR("notInGameError");

  String name;

  @JsonValue
  public String getName() {
    return name;
  }



  PrivateMessageType(String name) {
    this.name = name;
  }
}
