package com.wildeparty.model.gameElements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wildeparty.model.cards.Card;
import com.wildeparty.model.cards.CardActionResult;
import com.wildeparty.model.cards.CardType;

public class Place implements Serializable, GameEntity {

  private int id;

  private List<Card> cards = new ArrayList<>();
  private PlaceType placeType;

  public Place() {
  }

  public Place(PlaceType placeType, int id) {
    this.placeType = placeType;
    this.id = id;
  }

  public void gatherCardActionResults(GameSnapshot gameSnapshot, Card playedCard,
      List<CardActionResult> cardActionResults) {
    if (playedCard.getCardAction() == null) {
      return;
    }
    CardActionResult result = playedCard.getCardAction().getActionResult(gameSnapshot, playedCard, this);
    if (result.isLegalTarget()) {
      cardActionResults.add(result);
    }
    for (Card card : cards) {
      card.gatherCardActionResults(gameSnapshot, playedCard, cardActionResults);
    }
  }

  public List<Card> getCards() {
    return cards;
  }

  public void setCards(List<Card> cards) {
    this.cards = cards;
  }

  public int getId() {
    return id;
  }

  public PlaceType getPlaceType() {
    return placeType;
  }

  public Card getCardToRight(int cardIndex) {
    if (cards.size() > cardIndex + 1) {
      return cards.get(cardIndex + 1);
    }
    return null;
  }

  public Card getCardTwoToRight(int cardIndex) {
    if (cards.size() > cardIndex + 2) {
      return cards.get(cardIndex + 2);
    }
    return null;
  }

  @JsonIgnore
  public CardType[] getAcceptedCardTypes() {
    return switch (this.placeType) {  
      case DECK -> CardType.getAllCardTypes();
      case DISCARD_PILE -> CardType.getAllCardTypes();
      case GUEST_CARD_ZONE -> new CardType[] { CardType.BFF, CardType.GUEST, CardType.ENCHANT };
      case HAND -> new CardType[0];
      case PLAYER_ENCHANTMENT -> new CardType[] { CardType.ENCHANT_PLAYER };
      case SPECIALS_ZONE -> new CardType[] { CardType.SPECIAL };
      case UNWANTEDS_ZONE -> new CardType[] { CardType.UNWANTED };
      default -> new CardType[0];
    };
  }

}
