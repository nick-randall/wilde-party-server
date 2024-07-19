package com.stomp.chat.model;

public enum CardType {
    GUEST("guest"), 
    UNWANTED("unwanted"), 
    INSTANT("instant"), 
    BLITZ("blitz"), 
    BFF("bff"), 
    ENCHANT("enchant"),
    SPECIAL("special"),
    ENCHANT_PLAYER("enchantPlayer");
  
    private String name;
  
    public String getName() {
      return name;
    }
  
    private CardType(String name) {
      this.name = name;
    }
  
    static public CardType[] getAllCardTypes() {
      CardType[] allCardTypes = new CardType[7];
      int i = 0;
      for (CardType type : CardType.values()) {
        allCardTypes[i] = type;
        i++;
      }
      return allCardTypes;
    }
  
  }

