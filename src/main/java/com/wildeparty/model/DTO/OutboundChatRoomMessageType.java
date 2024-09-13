package com.wildeparty.model.DTO;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OutboundChatRoomMessageType {
  INVITE("invite"),
  ACCEPT("accept"),
  DECLINE("decline"),
  UPDATE("update");

  private String name;

  @JsonValue
  public String getName() {
    return this.name;
  }

  OutboundChatRoomMessageType(String name) {
    this.name = name;
  }


}