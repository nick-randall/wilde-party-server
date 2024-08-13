package com.stomp.chat.model;

public enum PlaceType {

  GUEST_CARD_ZONE("guestCardZone"),
  ENCHANTMENTS_ROW("enchantmentsZone"),
  UNWANTEDS_ZONE("unwantedsZone"),
  SPECIALS_ZONE("specialsZone"),
  HAND("hand"),
  DECK("deck"),
  DISCARD_PILE("discardPile"),
  PLAYER_ENCHANTMENT("playerEnchantment");

  private String name;

  public String getName() {
    return name;
  }

  private PlaceType(String name) {
    this.name = name;
  }
}
