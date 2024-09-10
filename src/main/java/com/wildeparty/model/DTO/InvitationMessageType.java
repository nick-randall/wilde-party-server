package com.wildeparty.model.DTO;

import com.fasterxml.jackson.annotation.JsonValue;

public enum InvitationMessageType {
  INVITE("invite"),
  ACCEPT("accept"),
  DECLINE("decline");

  private String name;

  @JsonValue
  public String getName() {
    return this.name;
  }

  InvitationMessageType(String name) {
    this.name = name;
  }


}