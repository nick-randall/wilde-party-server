package com.wildeparty.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class PlayerPlaces implements Serializable {

  @JsonIgnore
  private Place[] allPlaces = new Place[5];
  private Place guestCardZone;
  private Place enchantmentsRow;
  private Place hand;
  private Place unwantedsZone;
  private Place specialsZone;

  public PlayerPlaces(int playerIndex) {
   initPlaces(playerIndex);
  }

  public PlayerPlaces() {
  }

  private void initPlaces(int playerIndex) {
    System.out.println("Player index: " + playerIndex);
    int playerSuffix = playerIndex * 100;
    guestCardZone = new Place(PlaceType.GUEST_CARD_ZONE, playerSuffix + 1);
    enchantmentsRow = new Place(PlaceType.ENCHANTMENTS_ROW, playerSuffix + 2);
    hand = new Place(PlaceType.HAND,  playerSuffix + 3);
    unwantedsZone = new Place(PlaceType.UNWANTEDS_ZONE, playerSuffix + 4);
    specialsZone = new Place(PlaceType.SPECIALS_ZONE, playerSuffix + 5);
    this.allPlaces = new Place[] { guestCardZone, enchantmentsRow, unwantedsZone, hand, specialsZone };
  }

  public Place[] getAllPlaces() {
    return allPlaces;
  }

  public void setAllPlaces(Place[] allPlaces) {
    this.allPlaces = allPlaces;
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

  public Place getHand() {
    return allPlaces[3];
  }

  public Place getSpecialsZone() {
    return allPlaces[4];
  }

}
