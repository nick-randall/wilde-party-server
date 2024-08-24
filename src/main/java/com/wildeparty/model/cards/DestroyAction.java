package com.wildeparty.model.cards;

import com.wildeparty.model.GameSnapshot;
import com.wildeparty.model.LegalTargetType;
import com.wildeparty.model.PlaceType;
import com.wildeparty.model.SnapshotUpdateData;
import com.wildeparty.model.SnapshotUpdateType;

public class DestroyAction extends CardAction {

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
  public CardActionResult getActionResult(GameSnapshot gameSnapshot, Card playedCard, Object target) {
    if (!isLegalTargetOf(gameSnapshot, playedCard, target)) {
      return new CardActionResult(false);
    }
    Card targetCard = (Card) target;
    SnapshotUpdater updater = new SnapshotUpdater(gameSnapshot);

    updater.moveCardToDiscardPile(targetCard);
    GameSnapshot updatedSnapshot = updater.moveCardToDiscardPile(playedCard);

    SnapshotUpdateData updateData = new SnapshotUpdateData(SnapshotUpdateType.DESTROY, targetCard.getId(),
        playedCard.getId());
    gameSnapshot.setSnapshotUpdateData(updateData);
    CardActionResult result = new CardActionResult(true, LegalTargetType.CARD, updatedSnapshot,
        CardActionType.DESTROY);

    return result;

  }

}