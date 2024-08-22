package com.stomp.chat.model.cards;

import java.util.List;

import com.stomp.chat.model.GameSnapshot;
import com.stomp.chat.model.PlaceType;
import com.stomp.chat.model.SnapshotUpdateData;
import com.stomp.chat.model.SnapshotUpdateType;

public class DestroyAction implements CardAction {
  GameSnapshotUtils utils = new GameSnapshotUtils();
  ConditionChecker checker = new ConditionChecker();

  @Override
  public boolean isLegalTargetOf(GameSnapshot gameSnapshot, Card playedCard, Object target) {
    if (!(target instanceof Card)) {
      return false;
    }
    Card targetCard = (Card) target;
    boolean targetCardIsGuest = targetCard.getCardType() == CardType.GUEST;
    boolean targetCardIsNotProtected = !utils.isCardProtected(gameSnapshot, targetCard);
    boolean targetCardIsNotInHand = utils.findCard(targetCard, gameSnapshot).place.getPlaceType() != PlaceType.HAND;
    checker.setConditions(targetCardIsGuest, targetCardIsNotProtected, targetCardIsNotInHand);
    return checker.areAllTrue();
  }

  @Override
  public GameSnapshot updateSnapshot(GameSnapshot gameSnapshot, Card playedCard, Object target) {
    Card targetCard = (Card) target;
    SnapshotUpdater updater = new SnapshotUpdater(gameSnapshot);

    updater.moveCardToDiscardPile(targetCard);
    updater.moveCardToDiscardPile(playedCard);
    
    SnapshotUpdateData updateData = new SnapshotUpdateData(SnapshotUpdateType.DESTROY, targetCard.getId(),
        playedCard.getId());
    gameSnapshot.setSnapshotUpdateData(updateData);

    return gameSnapshot;

  }

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
