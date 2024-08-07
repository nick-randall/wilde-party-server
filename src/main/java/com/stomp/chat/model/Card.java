package com.stomp.chat.model;

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
  private CardActionType actionType;
  private LegalTargetType legalTargetType = LegalTargetType.PLACE;
  private TargetPlayerType legalTargetOwnerType = TargetPlayerType.SELF;
  private TargetPlayerType legalPlayerType = null;
  private CardType[] legalCardTargets = new CardType[] {};
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
