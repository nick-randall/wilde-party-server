package com.wildeparty.model.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;
import com.wildeparty.model.gameElements.GameSnapshot;

public record OutboundPersonalGameMessage(
    MessageType type,
    List<GameSnapshot> initialGameSnapshots) {

  public enum MessageType {
    INITIAL_GAME_SNAPSHOTS("initialGameSnapshots"),
    NOT_IN_GAME_ERROR("notInGameError");

    String name;

    @JsonValue
    public String getName() {
      return name;
    }

    MessageType(String name) {
      this.name = name;
    }
  }

}
