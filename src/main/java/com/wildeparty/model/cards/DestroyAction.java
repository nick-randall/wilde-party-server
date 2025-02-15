package com.wildeparty.model.cards;

import com.wildeparty.model.SnapshotUpdateData;
import com.wildeparty.model.SnapshotUpdateType;
import com.wildeparty.model.gameElements.GameEntity;
import com.wildeparty.model.gameElements.GameSnapshot;
import com.wildeparty.model.gameElements.LegalTargetType;
import com.wildeparty.model.gameElements.PlaceType;

public class DestroyAction extends CardAction {

  @Override
  public boolean isLegalTargetOf(GameSnapshot gameSnapshot, Card playedCard, GameEntity target) {
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
  public CardActionResult getActionResult(GameSnapshot gameSnapshot, Card playedCard, GameEntity target) {
    if (!isLegalTargetOf(gameSnapshot, playedCard, target)) {
      return new CardActionResult(target.getId(), false);
    }
    Card targetCard = (Card) target;
    SnapshotUpdater updater = new SnapshotUpdater(gameSnapshot);

    updater.moveCardToDiscardPile(targetCard);
    // GameSnapshot updatedSnapshot = updater.moveCardToDiscardPile(playedCard);

    SnapshotUpdateData updateData = new SnapshotUpdateData(SnapshotUpdateType.DESTROY, targetCard.getId(),
        playedCard.getId());
    // gameSnapshot.setSnapshotUpdateData(updateData);
    CardActionResult result = new CardActionResult(target.getId(), true, LegalTargetType.CARD, updateData,
        CardActionType.DESTROY);

    return result;

  }

}