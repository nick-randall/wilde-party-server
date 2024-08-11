package com.stomp.chat.model;

import org.hibernate.event.service.spi.DuplicationStrategy.Action;

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
  private PlaceType[] legalPlaceTargets = new PlaceType[] { PlaceType.GUEST_CARD_ZONE };

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

  public Card() {
  }

  public Card(Long id, String name, String image, CardType cardType) {
    super();
    this.id = id;
    this.name = name;
    this.image = image;
    this.cardType = cardType;
    switch (cardType) {
      case GUEST:
        this.actionType = CardActionType.ADD_DRAGGED;
        this.legalCardTargets = new CardType[0];
        this.legalPlaceTargets = new PlaceType[] { PlaceType.GUEST_CARD_ZONE };
        this.legalTargetOwnerType = TargetPlayerType.SELF;
        break;

      default:
        break;
    }
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
