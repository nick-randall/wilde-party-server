package com.wildeparty.model.cards;

import com.wildeparty.model.GameSnapshot;
import com.wildeparty.model.LegalTargetType;
import com.wildeparty.model.Place;
import com.wildeparty.model.Player;
import java.util.Arrays;

public class AddDraggedAction extends CardAction {
  GameSnapshotUtils utils = new GameSnapshotUtils();

  @Override
  public boolean isLegalTargetOf(GameSnapshot gameSnapshot, Card playedCard, Object target) {
    if (!(target instanceof Place)) {
      return false;
    }
    Place targetPlace = (Place) target;
    Player placeOwner = utils.findPlace(targetPlace, gameSnapshot);
    Player cardOwner = utils.findCard(playedCard, gameSnapshot).player;

    boolean addingToOwnPlace = placeOwner == cardOwner;
    boolean placeAcceptsCardType = Arrays.asList(targetPlace.getAcceptedCardTypes()).contains(playedCard.getCardType());
    checker.setConditions(addingToOwnPlace, placeAcceptsCardType);
    return checker.areAllTrue();
  }

  @Override
  public CardActionResult getActionResult(GameSnapshot gameSnapshot, Card playedCard, Object target) {
    if (isLegalTargetOf(gameSnapshot, playedCard, target)) {
      return new CardActionResult(true, LegalTargetType.PLACE, null, CardActionType.ADD_DRAGGED);
    }
    return new CardActionResult(false);
  }

}
