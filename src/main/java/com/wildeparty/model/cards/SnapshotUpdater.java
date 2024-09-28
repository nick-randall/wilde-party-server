package com.wildeparty.model.cards;

import java.util.ArrayList;
import java.util.List;

import com.wildeparty.model.SnapshotUpdateData;
import com.wildeparty.model.SnapshotUpdateType;
import com.wildeparty.model.gameElements.GameSnapshot;
import com.wildeparty.model.gameElements.Place;
import com.wildeparty.model.gameElements.Player;

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
    return gameSnapshot.withUpdatedIndex();
  }

  public GameSnapshot drawCard(Player player) {
    Place deck = gameSnapshot.getNonPlayerPlaces().getDeck();
    Card card = deck.getCards().remove(0);
    player.getPlaces().getHand().getCards().add(card);
    return gameSnapshot.withUpdatedIndex();
  }

  public GameSnapshot drawCards(Player player, int numCards) {
    GameSnapshot gameSnapshot = GameSnapshot.cloneGameSnapshot(this.gameSnapshot);
    Place deck = gameSnapshot.getNonPlayerPlaces().getDeck();
    int[] drawnCardIds = new int[numCards];
    for (int i = 0; i < numCards; i++) {
      Card drawnCard = deck.getCards().remove(0);
      player.getPlaces().getHand().getCards().add(drawnCard);
      drawnCardIds[i] = drawnCard.getId();
    }
    SnapshotUpdateData updateData = new SnapshotUpdateData(SnapshotUpdateType.DEALING_INITIAL_CARDS, -1, drawnCardIds);
    gameSnapshot.setSnapshotUpdateData(updateData);
    return gameSnapshot.withUpdatedIndex();
  }

  public static GameSnapshot staticdrawCards(int playerIndex, int numCards, GameSnapshot original) {
    GameSnapshot gameSnapshot = GameSnapshot.cloneGameSnapshot(original);
    Place deck = gameSnapshot.getNonPlayerPlaces().getDeck();
    int[] drawnCardIds = new int[numCards];
    for (int i = 0; i < numCards; i++) {
      Card drawnCard = deck.getCards().remove(0);
      gameSnapshot.getPlayers().get(playerIndex).getPlaces().getHand().getCards().add(drawnCard);
      drawnCardIds[i] = drawnCard.getId();
    }
    SnapshotUpdateData updateData = new SnapshotUpdateData(SnapshotUpdateType.DEALING_INITIAL_CARDS, -1, drawnCardIds);
    gameSnapshot.setSnapshotUpdateData(updateData);
    GameSnapshot updated =  gameSnapshot.withUpdatedIndex();
    return updated;

  }

  public GameSnapshot dealStartingGuest(int playerIndex) {
    GameSnapshot gameSnapshot = GameSnapshot.cloneGameSnapshot(this.gameSnapshot);
    Card card = gameSnapshot.getNonPlayerPlaces().getDeck().getCards().remove(0);
    gameSnapshot.getPlayers().get(playerIndex).getPlaces().getGuestCardZone().getCards().add(card);
    SnapshotUpdateData updateData = new SnapshotUpdateData(SnapshotUpdateType.DEALING_STARTING_GUEST, card.getId(),
        new int[] { card.getId() });
    gameSnapshot.setSnapshotUpdateData(updateData);

    return gameSnapshot.withUpdatedIndex();
  }

}
