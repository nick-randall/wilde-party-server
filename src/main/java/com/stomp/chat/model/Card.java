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

  CardActionType getCardActionType() {
    return switch (cardType) {
      case GUEST -> CardActionType.ADD_DRAGGED;
      case UNWANTED -> CardActionType.ADD_DRAGGED;
      case SPECIAL -> CardActionType.ADD_DRAGGED;
      case INTERRUPT -> CardActionType.INTERRUPT;
      case BFF -> CardActionType.ENCHANT_WITH_BFF;
      case ENCHANT -> CardActionType.ENCHANT;
      case STEAL -> CardActionType.STEAL;
      case SWAP -> CardActionType.SWAP;
      case DESTROY -> CardActionType.DESTROY;
      case ENCHANT_PLAYER -> CardActionType.ENCHANT_PLAYER;
      case SORCERY_ON_PLAYER -> CardActionType.SORCERY_ON_PLAYER;
    };
  }

  /**
   * PS it's always a Guest card, if it's a card.
   */
  LegalTargetType getLegalTargetType() {
    return switch (cardType) {
      case GUEST -> LegalTargetType.PLACE;
      case UNWANTED -> LegalTargetType.PLACE;
      case SPECIAL -> LegalTargetType.PLACE;
      case INTERRUPT -> LegalTargetType.NONE;
      case BFF -> LegalTargetType.CARD;
      case ENCHANT -> LegalTargetType.CARD;
      case STEAL -> LegalTargetType.CARD;
      case SWAP -> LegalTargetType.CARD;
      case DESTROY -> LegalTargetType.CARD;
      case ENCHANT_PLAYER -> LegalTargetType.PLAYER;
      case SORCERY_ON_PLAYER -> LegalTargetType.PLAYER;
    };
  }

  TargetPlayerType getLegalTargetOwnerType() {
    return switch (cardType) {
      case GUEST -> TargetPlayerType.SELF;
      case UNWANTED -> TargetPlayerType.SELF;
      case SPECIAL -> TargetPlayerType.SELF;
      case INTERRUPT -> TargetPlayerType.ENEMY;
      case BFF -> TargetPlayerType.SELF;
      case STEAL -> TargetPlayerType.ENEMY;
      case SWAP -> TargetPlayerType.ENEMY;
      case DESTROY -> TargetPlayerType.SELF;
      case ENCHANT ->
        this.name.toLowerCase() == "perplex" ? TargetPlayerType.ENEMY : TargetPlayerType.SELF;
      case ENCHANT_PLAYER ->
        this.name.toLowerCase() == "stromausfall" ? TargetPlayerType.ENEMY : TargetPlayerType.SELF;
      case SORCERY_ON_PLAYER -> TargetPlayerType.SELF;
    };
  }

  PlaceType getLegalTargetPlaceType() {
    return switch (cardType) {
      case GUEST -> PlaceType.GUEST_CARD_ZONE;
      case SPECIAL -> PlaceType.SPECIALS_ZONE;
      case UNWANTED -> PlaceType.UNWANTEDS_ZONE;
      case BFF -> null;
      case DESTROY -> null;
      case ENCHANT -> null;
      case ENCHANT_PLAYER -> null;
      case INTERRUPT -> null;
      case SORCERY_ON_PLAYER -> null;
      case STEAL -> null;
      case SWAP -> null;
      default -> null;
    };
  }

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
