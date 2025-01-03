package com.wildeparty.model.DTO;

import java.util.List;

import com.wildeparty.model.User;
import com.wildeparty.model.gameElements.GameSnapshot;

public class OutgoingGameMessage {

  private OutgoingGameMessageType type;
  private List<GameSnapshot> newSnapshots;
  private List<User> activePlayers;
  private String message;
  public OutgoingGameMessageType getType() {
    return type;
  }
  public OutgoingGameMessage(List<GameSnapshot> newSnapshots) {
    this.type = OutgoingGameMessageType.GAME_SNAPSHOTS;
    this.newSnapshots = newSnapshots;
  }

  public void setType(OutgoingGameMessageType type) {
    this.type = type;
  }
  public List<GameSnapshot> getNewSnapshots() {
    return newSnapshots;
  }
  public void setNewSnapshots(List<GameSnapshot> newSnapshots) {
    this.newSnapshots = newSnapshots;
  }
  public List<User> getActivePlayers() {
    return activePlayers;
  }
  public void setActivePlayers(List<User> activePlayers) {
    this.activePlayers = activePlayers;
  }
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }


}

