package com.wildeparty.model.cards;

import java.io.Serializable;

import com.wildeparty.model.gameElements.GameSnapshot;
import com.wildeparty.model.gameElements.LegalTargetType;

public class CardActionResult implements Serializable{
  boolean isLegalTarget;
  GameSnapshot resultingGameSnapshot;
  LegalTargetType targetType;
  CardActionType actionType;

  public boolean isLegalTarget() {
    return isLegalTarget;
  }

  public void setLegalTarget(boolean isLegalTarget) {
    this.isLegalTarget = isLegalTarget;
  }

  public GameSnapshot getResultingGameSnapshot() {
    return resultingGameSnapshot;
  }

  public void setResultingGameSnapshot(GameSnapshot resultingGameSnapshot) {
    this.resultingGameSnapshot = resultingGameSnapshot;
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

  public CardActionResult(boolean isLegalTarget) {
    this.isLegalTarget = isLegalTarget;
  }

  public CardActionResult(boolean isLegalTarget, LegalTargetType targetType, GameSnapshot resultingGameSnapshot,
      CardActionType actionType) {
    this.isLegalTarget = isLegalTarget;
    this.resultingGameSnapshot = resultingGameSnapshot;
    this.targetType = targetType;
    this.actionType = actionType;
  }
}
