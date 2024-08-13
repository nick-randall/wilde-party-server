package com.stomp.chat.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Place implements Serializable{

  private long id;

  private List<Card> cards = new ArrayList<>();
  private PlaceType placeType;

  public Place() {
  }

  public Place(PlaceType placeType) {
    this.placeType = placeType;
  }

  // public static Place findById(long id, EntityManager em) {
  //   List<Place> results = em.createNamedQuery("findById", Place.class)
  //       .setParameter("id", id)
  //       .getResultList();
  //   return results.isEmpty() ? null : results.get(0);
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

}
