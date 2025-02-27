package com.wildeparty.model.DTO;

import com.wildeparty.model.gameElements.GameSnapshot;

public class InboundGameMessage {

  private InboundGameMessageType type;
  private GameSnapshot gameSnapshot;

  public InboundGameMessage() {
  }

  void setGameSnapshot(GameSnapshot gameSnapshot) {
    this.gameSnapshot = gameSnapshot;
  }

  public InboundGameMessageType getType() {
    return type;
  }

  void setType(InboundGameMessageType type) {
    this.type = type;
  }

  public GameSnapshot getGameSnapshot() {
    return gameSnapshot;
  }

}
