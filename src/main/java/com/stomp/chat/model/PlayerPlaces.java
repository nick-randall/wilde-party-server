package com.stomp.chat.model;

import java.io.Serializable;

public class PlayerPlaces implements Serializable {
  private long id;

  public long getId() {
    return id;
  }

  private Place guestCardZone;
  private Place unwantedsZone;
  private Place hand;
  private Place specialsZone;

  public PlayerPlaces() {
    initPlaces();
  }

  private void initPlaces() {
    guestCardZone = new Place(PlaceType.GUEST_CARD_ZONE);
    hand = new Place(PlaceType.HAND);
    unwantedsZone = new Place(PlaceType.UNWANTEDS_ZONE);
    specialsZone = new Place(PlaceType.SPECIALS_ZONE);
  }

  public Place getGuestCardZone() {
    return guestCardZone;
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
