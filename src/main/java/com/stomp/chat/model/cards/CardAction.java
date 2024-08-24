package com.stomp.chat.model.cards;

import com.stomp.chat.model.GameSnapshot;

public abstract class CardAction {
  GameSnapshotUtils utils = new GameSnapshotUtils();
  ConditionChecker checker = new ConditionChecker();
  // SnapshotUpdater snapshotUpdater;

  public abstract boolean isLegalTargetOf(GameSnapshot gameSnapshot, Card playedCard, Object target);

  public abstract CardActionResult getActionResult(GameSnapshot gameSnapshot, Card playedCard, Object target);

}


class ConditionChecker {
  Boolean[] booleans;

  public boolean areAllTrue() {
    for (boolean bool : booleans) {
      if (!bool) {
        return false;
      }
    }
    return true;
  }

  public void setConditions(Boolean... booleans) {
    this.booleans = booleans;
  }
}
