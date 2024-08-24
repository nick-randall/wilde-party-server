package com.wildeparty.model.cards;

import com.wildeparty.model.GameSnapshot;
import com.wildeparty.model.Player;

public class SnapshotUpdater {
  GameSnapshot gameSnapshot;
  GameSnapshotUtils utils = new GameSnapshotUtils();

  public SnapshotUpdater(GameSnapshot gameSnapshot) {
    this.gameSnapshot = gameSnapshot;
  }

  public GameSnapshot moveCardToDiscardPile(Card card) {
    Location location = utils.findCard(card, gameSnapshot);
    location.place.getCards().remove(location.index);
    gameSnapshot.getDiscardPile().getCards().add(card);
    return gameSnapshot;
  }

  public GameSnapshot drawCard(Card card, Player player) {
    Location location = utils.findCard(card, gameSnapshot);
    location.place.getCards().remove(location.index);
    player.getPlaces().getHand().getCards().add(card);
    return gameSnapshot;
  }
  
}
