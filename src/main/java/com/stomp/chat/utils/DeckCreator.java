package com.stomp.chat.utils;

import java.util.Map;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.stomp.chat.model.Card;
import com.stomp.chat.model.CardType;
import com.stomp.chat.model.GuestCardType;

import jakarta.annotation.PostConstruct;

@Component
class DeckCreator {

  static private Long currId = 0L;

  static Long getCurrId() {
    return currId++;
  }

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

  static String[] enchantTypes = new String[] { "bffs",
      "zwilling",
      "perplex"
  };

  static String[] enchantPlayerTypes = new String[] {
      "glitzaglitza",
      "stromausfall",
  };

  static String[] unwantedsTypes = new String[] {
      "musikfuersichalleinebeansprucherin",
      "quasselstrippe",
      "partymuffel"
  };

  static String[] destroyTypes = new String[] {
      "nachbarin",
      "polizei",
      "schonsospaet"
  };

  static String[] swapTypes = new String[] {
      "falscheparty",
  };

  static String[] stealTypes = new String[] {
      "partyfluesterin",
      "bierleer"
  };

  static String[] playerSorceryTypes = new String[] {
      "wildeparty",
      "geburtstagskind",
    };

  static Map<CardType, Integer> numPerCardType = Map.ofEntries(
      Map.entry(CardType.BFF, 2),
      Map.entry(CardType.GUEST, 5),
      Map.entry(CardType.ENCHANT, 2),
      Map.entry(CardType.UNWANTED, 4),
      Map.entry(CardType.SPECIAL, 1),
      Map.entry(CardType.INTERRUPT, 2),
      Map.entry(CardType.STEAL, 2),
      Map.entry(CardType.SWAP, 2),
      Map.entry(CardType.DESTROY, 2),
      Map.entry(CardType.ENCHANT_PLAYER, 2),
      Map.entry(CardType.SORCERY_ON_PLAYER, 2));

  static List<Card> createGuests() {
    List<Card> guests = new ArrayList<Card>();

    int numGuestsPerType = numPerCardType.get(CardType.GUEST);
    for (GuestCardType guestCardType : GuestCardType.basicGuestCardTypes()) {
      for (int i = 0; i < numGuestsPerType; i++) {
        Card card = new Card();
        card.setCardType(CardType.GUEST);
        card.setName(guestCardType.getName() + i);
        card.setId(getCurrId());
        card.setGuestCardType(guestCardType);
        guests.add(card);
      }
    }
    return guests;
  }

  static List<Card> createStartGast(int numPlayers) {
    List<Card> startGasts = new ArrayList<Card>();
    GuestCardType[] startGuestTypes = Arrays.copyOfRange(GuestCardType.basicGuestCardTypes(), 0, numPlayers);
    for (GuestCardType guestCardType : startGuestTypes) {
      Card card = new Card();
      card.setCardType(CardType.GUEST);
      card.setGuestCardType(guestCardType);
      card.setName("startgast_" + guestCardType.getName());
      card.setId(getCurrId());
      startGasts.add(card);
    }
    return startGasts;
  }

  static List<Card> createSpecials() {
    List<Card> specials = new ArrayList<Card>();

    for (GuestCardType guestCardType : GuestCardType.basicGuestCardTypes()) {
      String[] specialsOfthisType = guestTypesAndSpecials.get(guestCardType);
      for (int i = 0; i < specialsOfthisType.length; i++) {
        Card card = new Card();
        card.setCardType(CardType.SPECIAL);
        card.setGuestCardType(guestCardType);
        card.setName(specialsOfthisType[i]);
        card.setId(getCurrId());
        specials.add(card);
      }
    }
    return specials;
  }

  static List<Card> createEnchantments() {
    return createCards(CardType.ENCHANT, enchantTypes);
  }

  static List<Card> createUnwanteds() {
    return createCards(CardType.UNWANTED, unwantedsTypes);
  }

  static List<Card> createDestroyCards() {
    return createCards(CardType.DESTROY, destroyTypes);
  }

  static List<Card> createDeck(int numPlayers) {
    List<Card> deck = new ArrayList<Card>();
    deck.addAll(createGuests());
    deck.addAll(createSpecials());
    deck.addAll(createEnchantments());
    deck.addAll(createUnwanteds());
    deck.addAll(createDestroyCards());
    deck.addAll(createEnchantPlayer());
    deck.addAll(createStartGast(numPlayers));
    return deck;
  }

  static List<Card> createEnchantPlayer() {
    return createCards(CardType.ENCHANT_PLAYER, enchantPlayerTypes);
  }

  static List<Card> createCards(CardType cardType, String[] types) {
    List<Card> cards = new ArrayList<Card>();
    int numCardsPerType = numPerCardType.get(cardType);
    for (String type : types) {
      for (int i = 0; i < numCardsPerType; i++) {
        Card card = new Card();
        card.setCardType(cardType);
        card.setName(type + i);
        card.setId(getCurrId());
        cards.add(card);
      }
    }
    return cards;

  }

  @PostConstruct
  static void main() {
    System.out.println("Creating deck");
    List<Card> deck = createDeck(3);
    Collections.shuffle(deck);
    for (Card card : deck) {
      System.out.println(card.getName() + "(" + card.getId() + ")" + " = " + card.getCardType());
    }
  }

}