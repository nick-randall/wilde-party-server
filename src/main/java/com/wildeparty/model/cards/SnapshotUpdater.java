package com.wildeparty.model.cards;

import java.util.ArrayList;
import java.util.List;

import com.wildeparty.model.GameSnapshot;
import com.wildeparty.model.Place;
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
    gameSnapshot.getNonPlayerPlaces().getDiscardPile().getCards().add(card);
    return gameSnapshot;
  }

  public GameSnapshot drawCard(Player player) {
    Place deck = gameSnapshot.getNonPlayerPlaces().getDeck();
    Card card = deck.getCards().remove(0);
    player.getPlaces().getHand().getCards().add(card);
    return gameSnapshot;
  }

  public GameSnapshot drawCards(Player player, int numCards) {
    Place deck = gameSnapshot.getNonPlayerPlaces().getDeck();
    for (int i = 0; i < numCards; i++) {
      Card drawnCard = deck.getCards().remove(0);
      player.getPlaces().getHand().getCards().add(drawnCard);
    }
    return gameSnapshot;
  }

}
