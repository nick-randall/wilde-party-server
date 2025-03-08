package com.wildeparty.utils;

import java.util.Map;

import com.wildeparty.model.cards.GuestCardType;

public class CardNames {
  static Map<GuestCardType, String[]> guestTypesAndSpecials = Map.of(
      GuestCardType.RUMGROELERIN,
      new String[] {
          "megaphon",
          "karaoke",
          "heliumballon",
          "meinsong",
          "smile"
      },
      GuestCardType.SAUFNASE,
      new String[] {
          "shots",
          "beerpong",
          "prost",
          "raucherzimmer",
          "barkeeperin"
      },
      GuestCardType.SCHLECKERMAUL,
      new String[] {
          "eisimbecher",
          "suessigkeiten",
          "midnightsnack",
          "fingerfood",
          "partypizza"
      },
      GuestCardType.TAENZERIN,
      new String[] {
          "nebelmaschine",
          "lichtshow",
          "discokugel",
          "poledance",
          "playlist"
      });

  public static String[] enchantNames = new String[] {
      "zwilling",
      "perplex"
  };

  public static String[] enchantPlayerNames = new String[] {
      "glitzaglitza",
      "stromausfall",
  };

  public static String[] unwantedsNames = new String[] {
      "musikfuersichalleinebeansprucherin",
      "quasselstrippe",
      "partymuffel"
  };

  public static String[] destroyNames = new String[] {
      "nachbarin",
      "polizei",
      "schonsospaet"
  };

  public static String[] swapTypes = new String[] {
      "falscheparty",
  };

  public static String[] stealTypes = new String[] {
      "partyfluesterin",
      "bierleer"
  };

  public static String[] playerSorceryTypes = new String[] {
      "wildeparty",
      "geburtstagskind",
      "biervorrat" // because its "non-interrupt" ability lets you draw a card, it is a sorcery
  };
}
