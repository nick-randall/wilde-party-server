package com.wildeparty.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class PlayerPlaces implements Serializable {

  @JsonIgnore
  private Place[] allPlaces;
  private Place guestCardZone = new Place(PlaceType.GUEST_CARD_ZONE);
  private Place enchantmentsRow = new Place(PlaceType.ENCHANTMENTS_ROW);
  private Place hand = new Place(PlaceType.HAND);
  private Place unwantedsZone = new Place(PlaceType.UNWANTEDS_ZONE);
  private Place specialsZone = new Place(PlaceType.SPECIALS_ZONE);

  public Place[] getAllPlaces() {
    return allPlaces;
  }

  public void setAllPlaces(Place[] allPlaces) {
    this.allPlaces = allPlaces;
  }

  public PlayerPlaces() {
   initPlaces();
  }

  private void initPlaces() {
    allPlaces = new Place[] { guestCardZone, enchantmentsRow, unwantedsZone, hand, specialsZone };
  }

  public Place getGuestCardZone() {
    return allPlaces[0];
  }

  public Place getEnchantmentsRow() {
    return allPlaces[1];
  }

  public Place getUnwantedsZone() {
    return allPlaces[2];
  }

  @JsonAlias("hand")
  public Place getHand() {
    return allPlaces[3];
  }

  public Place getSpecialsZone() {
    return allPlaces[4];
  }

}
