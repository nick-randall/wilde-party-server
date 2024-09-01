package com.wildeparty.model;
import java.io.Serializable;


public class Current implements Serializable{
  private int player;
  private TurnPhase phase;
  private int plays;
  private int draws;
  private int rolls;

  public Current() {
  }

  public static Current init() {
    int player = 0;
    TurnPhase phase = TurnPhase.DRAW_PHASE;
    int plays = 1;
    int draws = 1;
    int rolls = 1;
    return new Current(player, phase, plays, draws, rolls);
  }

  public Current(int player, TurnPhase phase, int plays, int draws, int rolls) {
    this.player = player;
    this.phase = phase;
    this.plays = plays;
    this.draws = draws;
    this.rolls = rolls;
  }

  public int getPlayer() {
    return player;
  }

  public void setPlayer(int player) {
    this.player = player;
  }

  public TurnPhase getPhase() {
    return phase;
  }

  public void setPhase(TurnPhase phase) {
    this.phase = phase;
  }

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

}
