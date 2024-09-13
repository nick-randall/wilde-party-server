package com.wildeparty.utils;

import java.util.ArrayList;
import java.util.List;

import com.wildeparty.model.Game;
import com.wildeparty.model.GameSnapshot;
import com.wildeparty.model.Player;
import com.wildeparty.model.SnapshotUpdateData;
import com.wildeparty.model.SnapshotUpdateType;
import com.wildeparty.model.cards.Card;
import com.wildeparty.model.cards.SnapshotUpdater;

public class SnapshotSetupUtil {
  public static List<GameSnapshot> setupInitialGameSnapshots(GameSnapshot snapshot) {
    List<GameSnapshot> snapshots = new ArrayList<GameSnapshot>();
    List<Card> deck = new DeckCreator().createDeck(snapshot.getPlayers().size());
    snapshot.getNonPlayerPlaces().getDeck().setCards(deck);
    SnapshotUpdateData updateData = new SnapshotUpdateData(SnapshotUpdateType.INITIAL_SNAPSHOT, -1, new int[0]);
    snapshot.setSnapshotUpdateData(updateData);
    snapshots.add(snapshot);

    for (int i = 0; i< snapshot.getPlayers().size(); i++) {
      SnapshotUpdater updater = new SnapshotUpdater(snapshot);
      snapshot = updater.dealStartingGuest(i);
      snapshots.add(snapshot);
    }

    for (int i = 0; i< snapshot.getPlayers().size(); i++) {
      // SnapshotUpdater updater = new SnapshotUpdater(snapshot);
      snapshot = SnapshotUpdater.staticdrawCards(i, 7, snapshot);
      snapshots.add(snapshot);
    }

    // for(GameSnapshot snap : snapshots) {
    GameSnapshot snap = snapshots.get(snapshots.size() -1);
      snap.getPlayers().forEach(player -> {
        List<Card> cardsInHand = player.getPlaces().getHand().getCards();
        System.out.println("number of cards in hand: " + cardsInHand.size());
        for(Card card: cardsInHand) {
          System.out.println(card.getImageName());
        }
        System.out.println("number of cards in guest zone: " + player.getPlaces().getGuestCardZone().getCards().size());
        List<Card> cardsInGuestZone = player.getPlaces().getGuestCardZone().getCards();
        for(Card card: cardsInGuestZone) {
          System.out.println(card.getImageName());
        }
      });
    // }
    for(GameSnapshot createdSnapshot : snapshots) {
      System.out.println("Snapshot: " + createdSnapshot.getId());
      SnapshotUpdateData updateData2 = createdSnapshot.getSnapshotUpdateData();
      System.out.println("Snapshot update data: " + updateData2.getType());
      System.out.println("Snapshot update data: " + updateData2.getPlayedCardIds());
      System.out.println("Snapshot update data: " + updateData2.getSecondaryCardId());


    }

    return snapshots;
  }

}
