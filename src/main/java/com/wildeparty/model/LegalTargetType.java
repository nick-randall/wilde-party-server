package com.wildeparty.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LegalTargetType {
  PLAYER("player"),
  PLACE("place"),
  CARD("card"),
  NONE("none");

  private String name;

  @JsonValue
  public String getName() {
    return name;
  }

  private LegalTargetType(String name) {
    this.name = name;
  }
}
