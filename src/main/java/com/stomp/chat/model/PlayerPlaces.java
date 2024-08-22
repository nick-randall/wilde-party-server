package com.stomp.chat.model;

import java.io.Serializable;

public class PlayerPlaces implements Serializable {

  public Place[] allPlaces;

  public PlayerPlaces() {
    allPlaces = initPlaces();
  }

  private Place[]  initPlaces() {
    Place guestCardZone = new Place(PlaceType.GUEST_CARD_ZONE);
    Place enchantmentsRow = new Place(PlaceType.ENCHANTMENTS_ROW);
    Place hand = new Place(PlaceType.HAND);
    Place unwantedsZone = new Place(PlaceType.UNWANTEDS_ZONE);
    Place specialsZone = new Place(PlaceType.SPECIALS_ZONE);
    return new Place[] {guestCardZone, enchantmentsRow, unwantedsZone, hand, specialsZone};
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
