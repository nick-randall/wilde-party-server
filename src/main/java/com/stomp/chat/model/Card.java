package com.stomp.chat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Card {
  @Id
  private Long id;
  private String name;
  private CardType cardType;
  private GuestCardType guestCardType;

  public void setGuestCardType(GuestCardType guestCardType) {
    this.guestCardType = guestCardType;
  }

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

  CardType getTargetCardType() {
    if (name.toLowerCase() == "tuersteher") {
      return CardType.UNWANTED;
    }
    return CardType.GUEST;
  }

  CardType[] getInterruptableCardTypes() {
    if (name.toLowerCase() == "tuersteher") {
      return new CardType[] { CardType.UNWANTED };
    } else if (name.toLowerCase() == "glitzaglitza") {
      return new CardType[] {
          CardType.SORCERY_ON_PLAYER,
          CardType.ENCHANT_PLAYER,
          CardType.STEAL,
          CardType.SWAP,
          CardType.DESTROY,
      };
    } else if (name.toLowerCase() == "biervorrat") {
      return new CardType[] { CardType.STEAL };
    }
    return new CardType[] {};

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
    if (cardType == CardType.GUEST) {
      return PlaceType.GUEST_CARD_ZONE;
    }
    if (cardType == CardType.UNWANTED) {
      return PlaceType.UNWANTEDS_ZONE;
    }
    if (cardType == CardType.SPECIAL) {
      return PlaceType.SPECIALS_ZONE;
    }
    return null;
  }

  public int getPointValue() {
    return switch (guestCardType) {
      case RUMGROELERIN -> 1;
      case SAUFNASE -> 1;
      case SCHLECKERMAUL -> 1;
      case TAENZERIN -> 1;
      case DOPPELT -> 2;
      case UNSCHEINBAR -> 1;
      default -> 0;
    };
  }

  public int getTakesUpSpaces() {
    return switch (guestCardType) {
      case RUMGROELERIN -> 1;
      case SAUFNASE -> 1;
      case SCHLECKERMAUL -> 1;
      case TAENZERIN -> 1;
      case DOPPELT -> 1;
      case UNSCHEINBAR -> 0;
      default -> 0;
    };
  }

  public LegalTargetType getHighlightType() {
    if (cardType == CardType.ENCHANT) {
      return LegalTargetType.CARD;
    }
    return getLegalTargetType();
  }

  public Card() {
  }

  public Card(Long id, String name, String image, CardType cardType, GuestCardType guestCardType) {
    super();
    this.id = id;
    this.name = name;
    this.cardType = cardType;
    this.guestCardType = guestCardType;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public CardType getCardType() {
    return cardType;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public GuestCardType getGuestCardType() {
    return guestCardType;
  }

  public void setCardType(CardType cardType) {
    this.cardType = cardType;
  }
}
