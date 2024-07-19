package com.stomp.chat.model;

public class PlayerPlaces {
  private long id;
  private Place guestCardZone;
  private Place unwantedsZone;
  private Place hand;
  private Place specialsZone;

  public PlayerPlaces() {
    initPlaces();

  }

  private void initPlaces() {
    CardType[] allCardTypes = CardType.getAllCardTypes();
    CardType[] guestAndEnchant = { CardType.BFF, CardType.GUEST, CardType.ENCHANT };
    CardType[] unwanteds = { CardType.UNWANTED };
    CardType[] specials = { CardType.SPECIAL };
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
