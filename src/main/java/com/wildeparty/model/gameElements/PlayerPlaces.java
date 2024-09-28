package com.wildeparty.model.gameElements;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PlayerPlaces implements Serializable {

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
  }

  @JsonIgnore
  public Place[] getAllPlaces() {
    return new Place[] { guestCardZone, enchantmentsRow, unwantedsZone, hand, specialsZone };
  }

  public Place getGuestCardZone() {
    return guestCardZone;
  }

  public Place getEnchantmentsRow() {
    return enchantmentsRow;
  }

  public Place getUnwantedsZone() {
    return unwantedsZone;
  }

  public Place getHand() {
    return hand;
  }

  public Place getSpecialsZone() {
    return specialsZone;
  }

}
