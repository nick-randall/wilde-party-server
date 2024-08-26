package com.wildeparty.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GameStatus {
  FINISHED("finished"),
  CANCELLED("cancelled"),
  STARTED("started");

  private String name;

  @JsonValue
  public String getName() {
    return name;
  }

  private GameStatus(String name) {
    this.name = name;
  }

}
