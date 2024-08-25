package com.wildeparty.utils;

import java.util.Map;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.wildeparty.model.cards.Card;
import com.wildeparty.model.cards.CardType;
import com.wildeparty.model.cards.GuestCardType;

import jakarta.annotation.PostConstruct;

@Component
public class DeckCreator {

  static private int currId = 0;

  static int getCurrId() {
    return currId++;
  }

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
        card.setImageName(guestCardType.getName() + i);
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
      card.setImageName("startgast_" + guestCardType.getName());
      card.setId(getCurrId());
      startGasts.add(card);
    }
    return startGasts;
  }

  static List<Card> createSpecials() {
    List<Card> specials = new ArrayList<Card>();

    for (GuestCardType guestCardType : GuestCardType.basicGuestCardTypes()) {
      String[] specialsOfthisType = CardNames.guestTypesAndSpecials.get(guestCardType);
      for (int i = 0; i < specialsOfthisType.length; i++) {
        Card card = new Card();
        card.setCardType(CardType.SPECIAL);
        card.setGuestCardType(guestCardType);
        card.setImageName(specialsOfthisType[i]);
        card.setId(getCurrId());
        specials.add(card);
      }
    }
    return specials;
  }

  static List<Card> createEnchantments() {
    return createCards(CardType.ENCHANT, CardNames.enchantNames);
  }

  static List<Card> createUnwanteds() {
    return createCards(CardType.UNWANTED, CardNames.unwantedsNames);
  }

  static List<Card> createDestroyCards() {
    return createCards(CardType.DESTROY, CardNames.destroyNames);
  }

  public static List<Card> createDeck(int numPlayers) {
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
    return createCards(CardType.ENCHANT_PLAYER, CardNames.enchantPlayerNames);
  }

  static List<Card> createCards(CardType cardType, String[] names) {
    List<Card> cards = new ArrayList<Card>();
    int numCardsPerType = numPerCardType.get(cardType);
    for (String name : names) {
      for (int i = 0; i < numCardsPerType; i++) {
        Card card = new Card();
        card.setCardType(cardType);
        card.setImageName(name + i);
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
      System.out.println(card.getImageName() + "(" + card.getId() + ")" + " = " + card.getCardType());
    }
   
   // Create game snapshot
    // GameSnapshot snapshot = new GameSnapshot();
    // List<Player> players = new ArrayList<>();
    // Player player = new Player();
    // player.setId(0L);
    // player.setName("francis");
    // player.setPlaces(new PlayerPlaces());
    // Player player2 = new Player();
    // player2.setId(1L);
    //  player.getPlaces().getHand().setCards(createStartGast(3));;

    // player2.setName("steven");
    // players.add(player);
    // players.add(player2);
    // snapshot.setPlayers(players);
    // GameSnapshotJsonConverter converter = new GameSnapshotJsonConverter();
    // System.out.println(converter.convertToDatabaseColumn(snapshot));
  }

}