package com.stomp.chat.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stomp.chat.model.cards.Action;
import com.stomp.chat.model.cards.Card;
import com.stomp.chat.model.cards.CardActionResult;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// @Entity
public class GameSnapshot implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  List<Player> players;

  private int currPlayer = 0;
  private TurnPhase currPhase = TurnPhase.DEAL_PHASE;
  private SnapshotUpdateData snapshotUpdateData;
  public Place[] allPlaces;

  public Place getDeck () {
    return allPlaces[0];
  }

  public Place getDiscardPile () {
    return allPlaces[1];
  }

  public GameSnapshot() {
  }

  public GameSnapshot(List<Player> players, int currPlayer, TurnPhase currPhase,
      SnapshotUpdateData snapshotUpdateData) {
    this.players = players;
    this.currPlayer = currPlayer;
    this.currPhase = currPhase;
    this.snapshotUpdateData = snapshotUpdateData;
    initPlaces();
  }

  private void initPlaces() {
    Place deck = new Place(PlaceType.DECK);
    Place discardPile = new Place(PlaceType.DISCARD_PILE);
    allPlaces = new Place[] {deck, discardPile};
  }

  // public void updateLegalTargets() {
  //   Map<Integer, TypeAndTargets> legalTargetsMap = new HashMap<Integer, TypeAndTargets>();
  //   List<Card> currentHandCards = players.get(currPlayer).getPlaces().getHand().getCards();
  //   for (Card card : currentHandCards) {
  //     Action action = Action.fromCard(card);
  //     for (Player player : players) {
  //       player.gatherLegalTargets(card.getId(), action, legalTargetsMap);
  //     }
  //   }
  // }
  public void updateLegalTargets() {
    Map<Integer, List<CardActionResult>> actionResultsMap = new HashMap<Integer, List<CardActionResult>>();
    List<Card> currentHandCards = players.get(currPlayer).getPlaces().getHand().getCards();
    for (Card card : currentHandCards) {
      List<CardActionResult> cardActionResults = new ArrayList<CardActionResult>();
      for (Player player : players) {
        player.gatherCardActionResults(this, card, cardActionResults);
      }
      actionResultsMap.put(card.getId(), cardActionResults);
    }
  }

  public int getCurrPlayer() {
    return this.currPlayer;
  }

  public void setCurrPlayer(int currPlayer) {
    this.currPlayer = currPlayer;
  }

  public TurnPhase getCurrPhase() {
    return this.currPhase;
  }

  public void setCurrPhase(TurnPhase currPhase) {
    this.currPhase = currPhase;
  }

  public SnapshotUpdateData getSnapshotUpdateData() {
    return this.snapshotUpdateData;
  }

  public void setSnapshotUpdateData(SnapshotUpdateData snapshotUpdateData) {
    this.snapshotUpdateData = snapshotUpdateData;
  }

  public GameSnapshot players(List<Player> players) {
    setPlayers(players);
    return this;
  }

  public GameSnapshot currPlayer(int currPlayer) {
    setCurrPlayer(currPlayer);
    return this;
  }

  public GameSnapshot currPhase(TurnPhase currPhase) {
    setCurrPhase(currPhase);
    return this;
  }

  public GameSnapshot snapshotUpdateData(SnapshotUpdateData snapshotUpdateData) {
    setSnapshotUpdateData(snapshotUpdateData);
    return this;
  }

  @Override
  public String toString() {
    return "{" +
        " players='" + getPlayers() + "'" +
        ", currPlayer='" + getCurrPlayer() + "'" +
        ", currPhase='" + getCurrPhase() + "'" +
        ", snapshotUpdateData='" + getSnapshotUpdateData() + "'" +
        "}";
  }

  public List<Player> getPlayers() {
    return players;
  }

  public void setPlayers(List<Player> players) {
    this.players = players;
  }

}
