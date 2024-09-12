package com.wildeparty.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class NonPlayerPlaces implements Serializable {

  @JsonIgnore
  private Place[] allPlaces;
  private Place deck;
  private Place discardPile;

  public Place getDeck() {
    return deck;
  }

  public Place getDiscardPile() {
    return discardPile;
  }

  @JsonIgnore
  public Place[] getAllPlaces() {
    return new Place[] { deck, discardPile };
  }

  public void setAllPlaces(Place[] allPlaces) {
    this.allPlaces = allPlaces;
  }

  public NonPlayerPlaces() {
    initPlaces();
  }

  private void initPlaces() {
    deck = new Place(PlaceType.DECK, 501);
    discardPile = new Place(PlaceType.DISCARD_PILE, 502);
    allPlaces = new Place[] { deck, discardPile };
  }

}
