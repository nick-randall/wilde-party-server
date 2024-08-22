package com.stomp.chat.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.aspectj.apache.bcel.generic.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stomp.chat.model.cards.Action;
import com.stomp.chat.model.cards.Card;
import com.stomp.chat.model.cards.CardType;

public class Place implements Serializable {

  private long id;

  private List<Card> cards = new ArrayList<>();
  private PlaceType placeType;

  public Place() {
  }

  public Place(PlaceType placeType) {
    this.placeType = placeType;
  }

  // public static Place findById(long id, EntityManager em) {
  // List<Place> results = em.createNamedQuery("findById", Place.class)
  // .setParameter("id", id)
  // .getResultList();
  // return results.isEmpty() ? null : results.get(0);
  // }

  public List<Card> getCards() {
    return cards;
  }

  public void setCards(List<Card> cards) {
    this.cards = cards;
  }

  public long getId() {
    return id;
  }

  public PlaceType getPlaceType() {
    return placeType;
  }

  public Card getCardToRight(int cardIndex) {
    if(cards.size() > cardIndex + 1) {
      return cards.get(cardIndex + 1);
    }
    return null;
  }

  public Card getCardTwoToRight(int cardIndex) {
    if(cards.size() > cardIndex + 2) {
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
      case HAND -> CardType.getAllCardTypes();
      case PLAYER_ENCHANTMENT -> new CardType[] { CardType.ENCHANT_PLAYER };
      case SPECIALS_ZONE -> new CardType[] { CardType.SPECIAL };
      case UNWANTEDS_ZONE -> new CardType[] { CardType.UNWANTED };
      default -> new CardType[0];
    };
  }
  private boolean isLegalTarget(Action action) {
    throw new UnsupportedOperationException("Not supported yet.");

  }

  public void gatherLegalTargets(Integer cardId, Action action, Map<Integer, TypeAndTargets> legalTargetsMap) {
    List<Integer> legalTargets = legalTargetsMap.get(cardId).getTargetCardIds();

    if (action.getLegalTargetType() == LegalTargetType.PLACE) {
      // logic for determining if this place is a legal target
      if(isLegalTarget(action)) {
        legalTargets.add(cardId);
      }
    } else {
      for (Card card : cards) {
        card.gatherLegalTargets(cardId, action, legalTargetsMap);
      }
      TypeAndTargets typeAndTargets = new TypeAndTargets(action.getLegalTargetType());
      typeAndTargets.setTargetCardIds(legalTargets);
      legalTargetsMap.put(cardId, typeAndTargets);
    }
  }

}
