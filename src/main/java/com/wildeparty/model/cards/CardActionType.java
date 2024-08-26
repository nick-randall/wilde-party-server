package com.wildeparty.model.cards;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CardActionType {
  ADD_DRAGGED("addDragged"),
  ENCHANT_WITH_BFF("enchantWithBff"),
  ENCHANT("enchant"),
  DESTROY("destroy"),
  STEAL("steal"),
  SWAP("swap"),
  ENCHANT_PLAYER("enchantPlayer"),
  SORCERY_ON_PLAYER("sorceryOnPlayer"),
  INTERRUPT("interrupt"),
  NONE("none");

  private String name;

  @JsonValue
  public String getName() {
    return name;
  }

  private CardActionType(String name) {
    this.name = name;
  }


}
