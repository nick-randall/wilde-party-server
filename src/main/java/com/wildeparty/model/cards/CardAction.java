package com.wildeparty.model.cards;

import com.wildeparty.model.gameElements.GameEntity;
import com.wildeparty.model.gameElements.GameSnapshot;

public abstract class CardAction {
  GameSnapshotUtils utils = new GameSnapshotUtils();
  ConditionChecker checker = new ConditionChecker();
  // SnapshotUpdater snapshotUpdater;

  public abstract boolean isLegalTargetOf(GameSnapshot gameSnapshot, Card playedCard, GameEntity target);

  public abstract CardActionResult getActionResult(GameSnapshot gameSnapshot, Card playedCard, GameEntity target);

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
