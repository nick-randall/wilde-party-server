package com.wildeparty.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wildeparty.model.cards.Card;
import com.wildeparty.model.cards.CardActionResult;

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
  private NonPlayerPlaces nonPlayerPlaces = new NonPlayerPlaces();


  Map<Integer, List<CardActionResult>> actionResultsMap = new HashMap<Integer, List<CardActionResult>>();

  public GameSnapshot() {
  }

  public GameSnapshot(List<Player> players, int currPlayer, TurnPhase currPhase,
      SnapshotUpdateData snapshotUpdateData) {
    this.players = players;
    this.currPlayer = currPlayer;
    this.currPhase = currPhase;
    this.snapshotUpdateData = snapshotUpdateData;
  }

  public void updateLegalTargets() {
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

  // @Override
  // public String toString() {
  //   return "{" +
  //       " players='" + getPlayers() + "'" +
  //       ", currPlayer='" + getCurrPlayer() + "'" +
  //       ", currPhase='" + getCurrPhase() + "'" +
  //       ", snapshotUpdateData='" + getSnapshotUpdateData() + "'" +
  //       "}";
  // }

  public List<Player> getPlayers() {
    return players;
  }

  public void setPlayers(List<Player> players) {
    this.players = players;
  }

  public NonPlayerPlaces getNonPlayerPlaces() {
    return nonPlayerPlaces;
  }

  public void setNonPlayerPlaces(NonPlayerPlaces nonPlayerPlaces) {
    this.nonPlayerPlaces = nonPlayerPlaces;
  }


}
