package com.stomp.chat.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.stomp.chat.User;
import com.stomp.chat.model.cards.Action;

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

  public void gatherLegalTargets(Integer cardId, Action action, Map<Integer, TypeAndTargets> legalTargetsMap) {
    List<Integer> legalTargets = legalTargetsMap.get(cardId).getTargetCardIds();
    if (action.getLegalTargetType() == LegalTargetType.PLAYER) {
      if (isLegalTarget(action)) {
        legalTargets.add(cardId);
      }
      // logic for determining if this player is a legal target
    } else {
      places.getSpecialsZone().gatherLegalTargets(cardId, action, legalTargetsMap);
      places.getGuestCardZone().gatherLegalTargets(cardId, action, legalTargetsMap);
      places.getHand().gatherLegalTargets(cardId, action, legalTargetsMap);
      places.getUnwantedsZone().gatherLegalTargets(cardId, action, legalTargetsMap);
    }
    TypeAndTargets typeAndTargets = new TypeAndTargets(action.getLegalTargetType());
    typeAndTargets.setTargetCardIds(legalTargets);
    legalTargetsMap.put(cardId, typeAndTargets);
  }

}
