package com.wildeparty.model;
import java.io.Serializable;

// import com.wildeparty.model.gameElements.TurnPhase;


public class Current implements Serializable{
  private int player;
  // private TurnPhase phase;
  private int plays;
  private int draws;
  private int rolls;
  private int counteringPlayer;

  public Current() {
  }

  public static Current init() {
    int player = 0;
    // TurnPhase phase = TurnPhase.DRAW_PHASE;
    int plays = 1;
    int draws = 1;
    int rolls = 1;
    int counteringPlayer = -1;
    return new Current(player, plays, draws, rolls, counteringPlayer);
  }

  public Current(int player, int plays, int draws, int rolls, int counteringPlayer) {
    this.player = player;
    // this.phase = phase;
    this.plays = plays;
    this.draws = draws;
    this.rolls = rolls;
    this.counteringPlayer = counteringPlayer;
  }

  public int getPlayer() {
    return player;
  }

  public void setPlayer(int player) {
    this.player = player;
  }

  // public TurnPhase getPhase() {
  //   return phase;
  // }

  // public void setPhase(TurnPhase phase) {
  //   this.phase = phase;
  // }

  public int getPlays() {
    return plays;
  }

  public void setPlays(int plays) {
    this.plays = plays;
  }

  public int getDraws() {
    return draws;
  }

  public void setDraws(int draws) {
    this.draws = draws;
  }

  public int getRolls() {
    return rolls;
  }

  public void setRolls(int rolls) {
    this.rolls = rolls;
  }

  public int getCounteringPlayer() {
    return counteringPlayer;
  }

  public void setCounteringPlayer(int counteringPlayer) {
    this.counteringPlayer = counteringPlayer;
  }

}
