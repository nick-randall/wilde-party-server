package com.wildeparty.model.gameElements;

import java.io.Serializable;
import java.util.List;

import com.wildeparty.model.User;
import com.wildeparty.model.cards.Card;
import com.wildeparty.model.cards.CardActionResult;
import com.wildeparty.utils.DeckCreator;

public class Player implements Serializable {
  private int id;

  private Long userId;

  private String name;

  private PlayerPlaces places;

  private boolean underProtection = false;

  public Player() {

  }

  TargetPlayerType targetPlayerType;

  public Player(User user, int playerIndex) {
    this.userId = user.getId();
    this.name = user.getName();
    this.id = playerIndex * 1000;
    this.places = new PlayerPlaces(playerIndex);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public PlayerPlaces getPlaces() {
    return places;
  }

  public void setPlaces(PlayerPlaces places) {
    this.places = places;
  }

  public boolean isUnderProtection() {
    return underProtection;
  }

  public void setUnderProtection(boolean underProtection) {
    this.underProtection = underProtection;
  }

  public void gatherCardActionResults(GameSnapshot gameSnapshot, Card playedCard,
      List<CardActionResult> cardActionResults) {
    CardActionResult result = playedCard.getAction().getActionResult(gameSnapshot, playedCard, this);
    cardActionResults.add(result);
    for (Place place : places.getAllPlaces()) {
      place.gatherCardActionResults(gameSnapshot, playedCard, cardActionResults);
    }
  }

}
