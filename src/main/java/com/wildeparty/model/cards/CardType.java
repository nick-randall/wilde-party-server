package com.wildeparty.model.cards;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CardType {
  GUEST("guest"),
  UNWANTED("unwanted"),
  SPECIAL("special"),
  ENCHANT("enchant"),
  BFF("bff"),
  DESTROY("destroy"),
  SWAP("swap"),
  STEAL("steal"),
  INTERRUPT("interrupt"),
  ENCHANT_PLAYER("enchantPlayer"),
  SORCERY_ON_PLAYER("sorceryOnPlayer");

  private String name;

  @JsonValue
  public String getName() {
    return name;
  }

  private CardType(String name) {
    this.name = name;
  }

  static public CardType[] getAllCardTypes() {
    CardType[] allCardTypes = new CardType[11];
    int i = 0;
    for (CardType type : CardType.values()) {
      allCardTypes[i] = type;
      i++;
    }
    return allCardTypes;
  }

}
