package com.wildeparty.model.cards;

import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.wildeparty.model.gameElements.GameEntity;
import com.wildeparty.model.gameElements.GameSnapshot;
import com.wildeparty.model.gameElements.PlaceType;
import com.wildeparty.model.gameElements.TargetPlayerType;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Card implements Serializable, GameEntity {
  private int id;
  private String imageName;
  private CardType cardType;
  private GuestCardType guestCardType;
  @JsonIgnore
  private CardAction action;
  @JsonIgnore // TODO just remove?
  private int pointValue;

  public void gatherCardActionResults(GameSnapshot gameSnapshot, Card playedCard,
      List<CardActionResult> cardActionResults) {
    CardActionResult result = playedCard.getAction().getActionResult(gameSnapshot, playedCard, this);
    cardActionResults.add(result);
  }

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

  // LegalTargetType getLegalTargetType() {
  // return switch (cardType) {
  // case GUEST -> LegalTargetType.PLACE;
  // case UNWANTED -> LegalTargetType.PLACE;
  // case SPECIAL -> LegalTargetType.PLACE;
  // case INTERRUPT -> LegalTargetType.NONE;
  // case BFF -> LegalTargetType.CARD;
  // case ENCHANT -> LegalTargetType.CARD;
  // case STEAL -> LegalTargetType.CARD;
  // case SWAP -> LegalTargetType.CARD;
  // case DESTROY -> LegalTargetType.CARD;
  // case ENCHANT_PLAYER -> LegalTargetType.PLAYER;
  // case SORCERY_ON_PLAYER -> LegalTargetType.PLAYER;
  // };
  // }

  CardType getTargetCardType() {
    if (getName().toLowerCase() == "tuersteher") {
      return CardType.UNWANTED;
    }
    return CardType.GUEST;
  }

  // String[] getInterruptableCardTypes() {
  // if (getName().toLowerCase() == "tuersteher") {
  // return CardNames.unwantedsNames;
  // } else if (getName().toLowerCase() == "glitzaglitza") {
  // return new String[] {
  // // CardNames.s, CardNames.SORCERY_ON_PLAYER,

  // CardNames.enchantPlayerNames,
  // CardNames.stealTypes,
  // CardNames.swapTypes,
  // CardNames.destroyNames,
  // };
  // } else if (getName().toLowerCase() == "biervorrat") {
  // return new CardType[] { CardType.STEAL };
  // }
  // return new CardType[] {};

  // }

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
        this.getName().toLowerCase() == "perplex" ? TargetPlayerType.ENEMY : TargetPlayerType.SELF;
      case ENCHANT_PLAYER ->
        this.getName().toLowerCase() == "stromausfall" ? TargetPlayerType.ENEMY : TargetPlayerType.SELF;
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

  // public void setPointValue() {
  //   int pointValue;
  //   if (guestCardType == null) {
  //     pointValue = 0;
  //   } else {
  //     pointValue = switch (guestCardType) {
  //       case RUMGROELERIN -> 1;
  //       case SAUFNASE -> 1;
  //       case SCHLECKERMAUL -> 1;
  //       case TAENZERIN -> 1;
  //       case DOPPELT -> 2;
  //       case UNSCHEINBAR -> 1;
  //       default -> 0;
  //     };
  //   }
  //   this.pointValue = pointValue;
  // }

  // public int getTakesUpSpaces() {
  //   if (guestCardType == null) {
  //     return 0;
  //   }
  //   return switch (guestCardType) {
  //     case RUMGROELERIN -> 1;
  //     case SAUFNASE -> 1;
  //     case SCHLECKERMAUL -> 1;
  //     case TAENZERIN -> 1;
  //     case DOPPELT -> 1;
  //     case UNSCHEINBAR -> 0;
  //     default -> 0;
  //   };
  // }

  // public LegalTargetType getHighlightType() {
  // if (cardType == CardType.ENCHANT) {
  // return LegalTargetType.CARD;
  // }
  // return getLegalTargetType();
  // }

  public Card() {
  }

  public Card(int id, String imageName, CardType cardType, GuestCardType guestCardType) {
    super();
    this.id = id;
    this.imageName = imageName;
    this.cardType = cardType;
    this.guestCardType = guestCardType;
  }

  public int getId() {
    return id;
  }

  @JsonIgnore
  public String getName() {
    Pattern pattern = Pattern.compile("[a-z_]+");
    Matcher matcher = pattern.matcher(imageName);
    matcher.find();
    return matcher.group(0);
  }

  public int getPointValue() {
    return pointValue;
  }

  public CardType getCardType() {
    return cardType;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setImageName(String imageName) {
    this.imageName = imageName;
  }

  public String getImageName() {
    return imageName;
  }

  public GuestCardType getGuestCardType() {
    return guestCardType;
  }

  public void setCardType(CardType cardType) {
    this.cardType = cardType;
  }

  public CardAction getAction() {
    return action;
  }

  public void setAction(CardAction action) {
    this.action = action;
  }

}
