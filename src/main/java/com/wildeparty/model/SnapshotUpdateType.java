package com.wildeparty.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SnapshotUpdateType {

  INITIAL_SNAPSHOT("initialSnapshot"),
  ADD_DRAGGED("addDragged"),
  DEALING_INITIAL_CARDS("dealingInitialCards"),
  DEALING_CARDS("dealingCards"),
  DEALING_STARTING_GUEST("dealingStartingGuest"),
  // REARRANGING_HAND("rearrangingHand"),
  REARRANGING_TABLE_PLACE("rearrangingTablePlace"),
  DRAWING_WILDE_PARTY("drawingWildeParty"),
  DESTROY("destroy"),
  STEAL("steal"),
  ENCHANT_WITH_BFF("enchantWithBff"),
  ENCHANT("enchant"),
  SWAP_CHOOSE_FIRST("swapChooseFirst"),
  SWAP_CHOOSE_SECOND("swapChooseSecond"),
  SORCERY_ON_PLAYER("sorceryOnPlayer"),;

  private String name;

  @JsonValue
  public String getName() {
    return name;
  }

  private SnapshotUpdateType(String name) {
    this.name = name;
  }

}
