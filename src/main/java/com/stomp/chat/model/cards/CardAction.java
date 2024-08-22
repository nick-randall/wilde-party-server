package com.stomp.chat.model.cards;

import org.hibernate.tool.schema.TargetType;

import com.stomp.chat.model.GameSnapshot;

public interface CardAction {

  public boolean isLegalTargetOf(GameSnapshot gameSnapshot, Card playedCard, Object target);

  public GameSnapshot updateSnapshot(GameSnapshot gameSnapshot, Card playedCard, Object target);

}

class CardActionResult {
  boolean isLegalTarget;

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

  public TargetType getTargetType() {
    return targetType;
  }

  public void setTargetType(TargetType targetType) {
    this.targetType = targetType;
  }

  public CardActionType getActionType() {
    return actionType;
  }

  public void setActionType(CardActionType actionType) {
    this.actionType = actionType;
  }

  GameSnapshot resultingGameSnapshot;
  TargetType targetType;
  CardActionType actionType;

  public CardActionResult(boolean isLegalTarget) {
    this.isLegalTarget = isLegalTarget;
  }

  public CardActionResult(boolean isLegalTarget, TargetType targetType, GameSnapshot resultingGameSnapshot,
      CardActionType actionType) {
    this.isLegalTarget = isLegalTarget;
    this.resultingGameSnapshot = resultingGameSnapshot;
    this.targetType = targetType;
    this.actionType = actionType;
  }
}