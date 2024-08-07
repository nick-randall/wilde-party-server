package com.stomp.chat.model;

import java.io.Serializable;
import java.util.List;

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
  

  public GameSnapshot() {
  }

  public GameSnapshot(List<Player> players, int currPlayer, TurnPhase currPhase, SnapshotUpdateData snapshotUpdateData) {
    this.players = players;
    this.currPlayer = currPlayer;
    this.currPhase = currPhase;
    this.snapshotUpdateData = snapshotUpdateData;
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
