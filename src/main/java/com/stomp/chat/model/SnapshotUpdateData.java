package com.stomp.chat.model;

import java.util.List;

public class SnapshotUpdateData {

  private SnapshotUpdateType type;
  private List<Long> playedCardIds; // only plural for dealt cards
  private Long targetId; // can be playerId, placeId, or cardId
  private Long secondaryCardId; // Nullable; necessary for swap

  public SnapshotUpdateData() {
  }

  public SnapshotUpdateData(SnapshotUpdateType type, List<Long> playedCardIds, Long targetId, Long secondaryCardId) {
    this.type = type;
    this.playedCardIds = playedCardIds;
    this.targetId = targetId;
    this.secondaryCardId = secondaryCardId;
  }

  public SnapshotUpdateData(SnapshotUpdateType type, List<Long> playedCardIds, Long targetId) {
    this.type = type;
    this.playedCardIds = playedCardIds;
    this.targetId = targetId;
    this.secondaryCardId = null;
  }

  public SnapshotUpdateType getType() {
    return this.type;
  }

  public void setType(SnapshotUpdateType type) {
    this.type = type;
  }

  public List<Long> getPlayedCardIds() {
    return this.playedCardIds;
  }

  public void setPlayedCardIds(List<Long> playedCardIds) {
    this.playedCardIds = playedCardIds;
  }

  public Long getTargetId() {
    return this.targetId;
  }

  public void setTargetId(Long targetId) {
    this.targetId = targetId;
  }

  public Long getSecondaryCardId() {
    return this.secondaryCardId;
  }

  public void setSecondaryCardId(Long secondaryCardId) {
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
