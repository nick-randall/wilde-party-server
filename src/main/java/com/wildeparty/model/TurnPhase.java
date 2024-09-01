package com.wildeparty.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TurnPhase {

  DEAL_PHASE("dealPhase"),
  PLAY_PHASE("playPhase"),
  DRAW_PHASE("drawPhase"),
  ROLL_PHASE("rollPhase"),
  CHOOSE_SECOND_SWAP_TARGET("chooseSecondSwapTarget"),
  COUNTER_PHASE("counterPhase");

  private String name;
  @JsonValue
  public String getName() {
    return name;
  }

  private TurnPhase(String name) {
    this.name = name;
  }

}
