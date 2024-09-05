package com.wildeparty.model.DTO;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PrivateMessageType {
  INVITE("invite"),
  ERROR_SUBSCRIBING("error_subscribing");

  String name;

  @JsonValue
  public String getName() {
    return name;
  }



  PrivateMessageType(String name) {
    this.name = name;
  }
}
