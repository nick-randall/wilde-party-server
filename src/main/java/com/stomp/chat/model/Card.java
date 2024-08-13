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
    switch (cardType) {
      case GUEST:
        return CardActionType.ADD_DRAGGED;
      case UNWANTED:
        return CardActionType.ADD_DRAGGED;
      case SPECIAL:
        return CardActionType.ADD_DRAGGED;
      case INTERRUPT:
        return CardActionType.INTERRUPT;
      case BFF:
        return CardActionType.ENCHANT_WITH_BFF;
      case ENCHANT:
        return CardActionType.ENCHANT;
      case STEAL:
        return CardActionType.STEAL;
      case SWAP:
        return CardActionType.SWAP;
      case DESTROY:
        return CardActionType.DESTROY;
      case ENCHANT_PLAYER:
        return CardActionType.ENCHANT_PLAYER;
      case SORCERY_ON_PLAYER:
        return CardActionType.SORCERY_ON_PLAYER;
      default:
        System.out.println("ERROR couldnt match card type with a CardActionType ");
        return CardActionType.NONE;
    }
  }

  /**
   * PS it's always a Guest card, if it's a card.
   */
  LegalTargetType getLegalTargetType() {
    switch (cardType) {
      case GUEST:
        return LegalTargetType.PLACE;
      case UNWANTED:
        return LegalTargetType.PLACE;
      case SPECIAL:
        return LegalTargetType.PLACE;
      case INTERRUPT:
        return LegalTargetType.NONE;
      case BFF:
        return LegalTargetType.CARD;
      case ENCHANT:
        return LegalTargetType.CARD;
      case STEAL:
        return LegalTargetType.CARD;
      case SWAP:
        return LegalTargetType.CARD;
      case DESTROY:
        return LegalTargetType.CARD;
      case ENCHANT_PLAYER:
        return LegalTargetType.PLAYER;
      case SORCERY_ON_PLAYER:
        return LegalTargetType.PLAYER;
      default:
        System.out.println("ERROR couldnt match CardType with a LegalTargetType");
        return LegalTargetType.NONE;
    }
  }

  TargetPlayerType getLegalTargetOwnerType() {
    switch (cardType) {
      case GUEST:
        return TargetPlayerType.SELF;
      case UNWANTED:
        return TargetPlayerType.SELF;
      case SPECIAL:
        return TargetPlayerType.SELF;
      case INTERRUPT:
        return TargetPlayerType.ENEMY;
      case BFF:
        return TargetPlayerType.SELF;
      case STEAL:
        return TargetPlayerType.ENEMY;
      case SWAP:
        return TargetPlayerType.ENEMY;
      case DESTROY:
        return TargetPlayerType.SELF;
      case ENCHANT:
        if (this.name.toLowerCase() == "perplex") {
          return TargetPlayerType.ENEMY;
        }
        return TargetPlayerType.SELF;
      case ENCHANT_PLAYER:
        if (this.name.toLowerCase() == "stromausfall") {
          return TargetPlayerType.ENEMY;
        }
        return TargetPlayerType.SELF;
      case SORCERY_ON_PLAYER:
        return TargetPlayerType.SELF;
      default:
        System.out.println("ERROR couldnt match CardType with a LegalTargetType");
        return TargetPlayerType.SELF;
    }
  }

  PlaceType getLegalTargetPlaceType() {
    switch (cardType) {
      case GUEST:
        return PlaceType.GUEST_CARD_ZONE;
      case SPECIAL:
        return PlaceType.SPECIALS_ZONE;
      case UNWANTED:
        return PlaceType.UNWANTEDS_ZONE;
      case BFF:
        return null;
      case DESTROY:
        return null;
      case ENCHANT:
        return null;
      case ENCHANT_PLAYER:
        return null;
      case INTERRUPT:
        return null;
      case SORCERY_ON_PLAYER:
        return null;
      case STEAL:
        return null;
      case SWAP:
        return null;
      default:
        return null;
    }
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
