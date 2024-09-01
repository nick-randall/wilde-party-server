package com.wildeparty.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PlaceType {

  GUEST_CARD_ZONE("guestCardZone"),
  ENCHANTMENTS_ROW("enchantmentsRow"),
  UNWANTEDS_ZONE("unwantedsZone"),
  SPECIALS_ZONE("specialsZone"),
  HAND("hand"),
  DECK("deck"),
  DISCARD_PILE("discardPile"),
  PLAYER_ENCHANTMENT("playerEnchantment");

  private String name;

  @JsonValue
  public String getName() {
    return name;
  }

  private PlaceType(String name) {
    this.name = name;
  }
}
