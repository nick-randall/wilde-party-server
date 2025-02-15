package com.wildeparty.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wildeparty.model.SnapshotUpdateData;
import com.wildeparty.model.SnapshotUpdateType;
import com.wildeparty.model.cards.Card;
import com.wildeparty.model.cards.CardActionResult;
import com.wildeparty.model.cards.SnapshotUpdater;
import com.wildeparty.model.gameElements.GameSnapshot;

public class SnapshotSetupUtil {
  public static List<GameSnapshot> setupInitialGameSnapshots(GameSnapshot snapshot) {
    List<GameSnapshot> snapshots = new ArrayList<GameSnapshot>();
    List<Card> deck = new DeckCreator().createDeck(snapshot.getPlayers().size());
    snapshot.getNonPlayerPlaces().getDeck().setCards(deck);
    snapshot.setIndex(0);
    SnapshotUpdateData updateData = new SnapshotUpdateData(SnapshotUpdateType.INITIAL_SNAPSHOT, -1, new int[0]);
    snapshot.setSnapshotUpdateData(updateData);
    snapshots.add(snapshot);

    for (int i = 0; i < snapshot.getPlayers().size(); i++) {
      SnapshotUpdater updater = new SnapshotUpdater(snapshot);
      snapshot = updater.dealStartingGuest(i);
      snapshots.add(snapshot);
    }

    for (int i = 0; i < snapshot.getPlayers().size(); i++) {
      snapshot = SnapshotUpdater.staticdrawCards(i, 7, snapshot);
      snapshots.add(snapshot);
    }

    snapshots.get(snapshots.size() - 1).updateLegalTargets();
    // Map<Integer, List<CardActionResult>> resMap = snapshots.get(snapshots.size() - 1).getActionResultsMap();
    // for (Map.Entry<Integer, List<CardActionResult>> entry: resMap.entrySet()) {
    //   List<CardActionResult> resList = entry.getValue();
    //   System.out.println("Card with id:" + entry.getKey() + "... can target " + resList.size() + " targets");

    //   for (CardActionResult res : resList) {
    //     if (res.isLegalTarget()) {
    //       System.out.println("... can target " + res.getTargetType() + "with " +res.getActionType());
    //     }
    //   }
    // }

    return snapshots;
  }

}
