package com.stomp.chat.model;

import java.util.List;

public class SnapshotUpdateData {

  private SnapshotUpdateType type;
  private int[] playedCardIds; // only plural for dealt cards
  private int targetId; // can be playerId, placeId, or cardId
  private int secondaryCardId; // Nullable; necessary for swap

  public SnapshotUpdateData() {
  }

  public SnapshotUpdateData(SnapshotUpdateType type) {
    this.type = type;
  }

  // public SnapshotUpdateData(SnapshotUpdateType type, int targetId, int secondaryCardId, int... playedCardIds) {
  //   this.type = type;
  //   this.playedCardIds = playedCardIds;
  //   this.targetId = targetId;
  //   this.secondaryCardId = secondaryCardId;
  // }

  public SnapshotUpdateData(SnapshotUpdateType type, int targetId, int... playedCardIds) {
    this.type = type;
    this.playedCardIds = playedCardIds;
    this.targetId = targetId;
    // this.secondaryCardId = null;
  }

  public SnapshotUpdateType getType() {
    return this.type;
  }

  public void setType(SnapshotUpdateType type) {
    this.type = type;
  }

  public int[] getPlayedCardIds() {
    return this.playedCardIds;
  }

  public void setPlayedCardIdsArray(int[] playedCardIds) {
    this.playedCardIds = playedCardIds;
  }

  public void setPlayedCardIds(int... playedCardIds) {
    this.playedCardIds = playedCardIds;
  }

  public Integer getTargetId() {
    return this.targetId;
  }

  public void setTargetId(Integer targetId) {
    this.targetId = targetId;
  }

  public Integer getSecondaryCardId() {
    return this.secondaryCardId;
  }

  public void setSecondaryCardId(Integer secondaryCardId) {
    this.secondaryCardId = secondaryCardId;
  }

  @Override
  public String toString() {
    return "{" +
        " type='" + getType() + "'" +
        ", playedCardIds='" + getPlayedCardIds() + "'" +
        ", targetId='" + getTargetId() + "'" +
        ", secondaryCardId='" + getSecondaryCardId() + "'" +
        "}";
  }

}
