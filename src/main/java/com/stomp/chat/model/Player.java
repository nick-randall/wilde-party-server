package com.stomp.chat.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.stomp.chat.User;
import com.stomp.chat.model.cards.Action;
import com.stomp.chat.model.cards.Card;
import com.stomp.chat.model.cards.CardAction;
import com.stomp.chat.model.cards.CardActionResult;

public class Player implements Serializable {
  private Long id;

  private Long userId;

  private String name;

  private PlayerPlaces places = new PlayerPlaces();

  private boolean underProtection = false;

  public Player() {

  }

  TargetPlayerType targetPlayerType;

  public Player(User user) {
    this.userId = user.getId();
    this.name = user.getName();
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  private boolean isLegalTarget(Action action) {
    throw new UnsupportedOperationException("Not supported yet.");

    // action.getCardActionType() == CardActionType.ENCHANT_PLAYER;
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
