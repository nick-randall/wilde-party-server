package com.stomp.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Card {
  @Id
  private Long id;
  private String name;
  private String image;
  private CardType cardType;
  // Action properties with an example (Guest card)
  @JsonIgnore
  private CardActionType actionType;
  @JsonIgnore
  private LegalTargetType legalTargetType = LegalTargetType.PLACE;
  @JsonIgnore
  private TargetPlayerType legalTargetOwnerType = TargetPlayerType.SELF;
  @JsonIgnore
  private TargetPlayerType legalPlayerType = null;
  @JsonIgnore
  private CardType[] legalCardTargets = new CardType[] {};
  @JsonIgnore
  private PlaceType[] legalPlaceTargets = new PlaceType[] {PlaceType.GUEST_CARD_ZONE};

  public Card() {
  }

  public Card(Long id, String name, String image, CardType cardType) {
    super();
    this.id = id;
    this.name = name;
    this.image = image;
    this.cardType = cardType;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getImage() {
    return image;
  }

  public CardType getCardType() {
    return cardType;
  }
}
