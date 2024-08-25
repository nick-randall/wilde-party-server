package com.wildeparty.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class NonPlayerPlaces implements Serializable {

  @JsonIgnore
  private Place[] allPlaces;
  private Place deck = new Place(PlaceType.DECK);
  private Place discardPile = new Place(PlaceType.DISCARD_PILE);

  public Place getDeck() {
    return allPlaces[0];
  }

  public Place getDiscardPile() {
    return allPlaces[1];
  }

  public Place[] getAllPlaces() {
    return allPlaces;
  }

  public void setAllPlaces(Place[] allPlaces) {
    this.allPlaces = allPlaces;
  }

  public NonPlayerPlaces() {
    initPlaces();
  }

  private void initPlaces() {
    allPlaces = new Place[] { deck, discardPile };
  }

}
