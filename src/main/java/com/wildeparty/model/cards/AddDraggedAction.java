package com.wildeparty.model.cards;

import java.util.Arrays;

import com.wildeparty.model.SnapshotUpdateData;
import com.wildeparty.model.SnapshotUpdateType;
import com.wildeparty.model.gameElements.GameEntity;
import com.wildeparty.model.gameElements.GameSnapshot;
import com.wildeparty.model.gameElements.LegalTargetType;
import com.wildeparty.model.gameElements.Place;
import com.wildeparty.model.gameElements.Player;

public class AddDraggedAction extends CardAction {
  GameSnapshotUtils utils = new GameSnapshotUtils();

  @Override
  public boolean isLegalTargetOf(GameSnapshot gameSnapshot, Card playedCard, GameEntity target) {
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
  public CardActionResult getActionResult(GameSnapshot gameSnapshot, Card playedCard, GameEntity target) {
    if (isLegalTargetOf(gameSnapshot, playedCard, target)) {
      SnapshotUpdateData updateData = new SnapshotUpdateData(SnapshotUpdateType.ADD_DRAGGED, 
          target.getId(), playedCard.getId());
      return new CardActionResult(target.getId(), true, LegalTargetType.PLACE, updateData, CardActionType.ADD_DRAGGED);
    }
    return new CardActionResult(target.getId(), false);
  }

}
