package com.wildeparty.model.cards;

import java.io.Serializable;

import com.wildeparty.model.SnapshotUpdateData;
import com.wildeparty.model.gameElements.GameSnapshot;
import com.wildeparty.model.gameElements.LegalTargetType;

public class CardActionResult implements Serializable {
  int targetId;
  boolean isLegalTarget;
  SnapshotUpdateData snapshotUpdateData;
  LegalTargetType targetType;
  CardActionType actionType;

  public boolean isLegalTarget() {
    return isLegalTarget;
  }

  public void setLegalTarget(boolean isLegalTarget) {
    this.isLegalTarget = isLegalTarget;
  }

  public LegalTargetType getTargetType() {
    return targetType;
  }

  public void setTargetType(LegalTargetType targetType) {
    this.targetType = targetType;
  }

  public CardActionType getActionType() {
    return actionType;
  }

  public void setActionType(CardActionType actionType) {
    this.actionType = actionType;
  }

  public CardActionResult(int targetId, boolean isLegalTarget) {
    this.targetId = targetId;
    this.isLegalTarget = isLegalTarget;
  }
  public SnapshotUpdateData getSnapshotUpdateData() {
    return snapshotUpdateData;
  }

  public void setSnapshotUpdateData(SnapshotUpdateData snapshotUpdateData) {
    this.snapshotUpdateData = snapshotUpdateData;
  }

  public CardActionResult(int targetId, boolean isLegalTarget, LegalTargetType targetType,
      SnapshotUpdateData snapshotUpdateData,
      CardActionType actionType) {
    this.targetId = targetId;
    this.isLegalTarget = isLegalTarget;
    this.snapshotUpdateData = snapshotUpdateData;
    this.targetType = targetType;
    this.actionType = actionType;
  }

  @Override
  public String toString() {
    return "CardActionResult [actionType=" + actionType + ", isLegalTarget=" + isLegalTarget + ", snapshotUpdateData="
        + snapshotUpdateData + ", targetId=" + targetId + ", targetType=" + targetType + "]";
  }

}
