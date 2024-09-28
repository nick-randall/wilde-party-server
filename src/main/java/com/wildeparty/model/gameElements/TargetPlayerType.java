package com.wildeparty.model.gameElements;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TargetPlayerType {
  SELF("self"),
  ENEMY("enemy"),;

  private String name;

  @JsonValue
  public String getName() {
    return name;
  }

  private TargetPlayerType(String name) {
    this.name = name;
  }

}
