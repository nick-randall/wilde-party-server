package com.stomp.chat.model;

public enum GuestCardType {

	SAUFNASE("saufnase"), 
  TAENZERIN("taenzerin"), 
  SCHLECKERMAUL("schleckermaul"), 
  RUMGROELERIN("rumgroelerin"),
  DOPPELT("doppelt"),
  UNSCHEINBAR("unscheinbar");
	
  private String name;

  public String getName() {
    return name;
  }

  private GuestCardType(String name) {
    this.name = name;
  }

  static public GuestCardType getGuestCardType(String name) {
    return switch (name) {
      case "saufnase" -> GuestCardType.SAUFNASE;
      case "taenzerin" -> GuestCardType.TAENZERIN;
      case "schleckermaul" -> GuestCardType.SCHLECKERMAUL;
      case "rumgroelerin" -> GuestCardType.RUMGROELERIN;
      default -> null;
    };
  }

  static public GuestCardType[] basicGuestCardTypes() {
    return new GuestCardType[] { SAUFNASE, TAENZERIN, SCHLECKERMAUL, RUMGROELERIN };
  }


}
