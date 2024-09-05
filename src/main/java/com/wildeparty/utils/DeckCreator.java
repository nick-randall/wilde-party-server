package com.wildeparty.utils;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.wildeparty.User;
import com.wildeparty.backend.GamesService;
import com.wildeparty.backend.GamesServiceImpl;
import com.wildeparty.backend.UserService;
import com.wildeparty.backend.UserServiceImpl;
import com.wildeparty.model.Game;
import com.wildeparty.model.cards.Card;
import com.wildeparty.model.cards.CardType;
import com.wildeparty.model.cards.GuestCardType;

import jakarta.annotation.PostConstruct;

@Component
public class DeckCreator {

  @Autowired
  UserService userService;
  @Autowired
  GamesService gamesService;

  private int currCardId = 0;

  public int getCurrCardId() {
    return currCardId++;
  }

  Map<CardType, Integer> numPerCardType = Map.ofEntries(
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

  List<Card> createGuests() {
    List<Card> guests = new ArrayList<Card>();

    int numGuestsPerType = numPerCardType.get(CardType.GUEST);
    for (GuestCardType guestCardType : GuestCardType.basicGuestCardTypes()) {
      for (int i = 0; i < numGuestsPerType; i++) {
        Card card = new Card();
        card.setCardType(CardType.GUEST);
        card.setImageName(guestCardType.getName() + i);
        card.setId(getCurrCardId());
        card.setGuestCardType(guestCardType);
        guests.add(card);
      }
    }
    return guests;
  }

  List<Card> createStartGast(int numPlayers) {
    List<Card> startGasts = new ArrayList<Card>();
    GuestCardType[] startGuestTypes = Arrays.copyOfRange(GuestCardType.basicGuestCardTypes(), 0, numPlayers);
    for (GuestCardType guestCardType : startGuestTypes) {
      Card card = new Card();
      card.setCardType(CardType.GUEST);
      card.setGuestCardType(guestCardType);
      card.setImageName("startgast_" + guestCardType.getName());
      card.setId(getCurrCardId());
      startGasts.add(card);
    }
    return startGasts;
  }

  List<Card> createSpecials() {
    List<Card> specials = new ArrayList<Card>();

    for (GuestCardType guestCardType : GuestCardType.basicGuestCardTypes()) {
      String[] specialsOfthisType = CardNames.guestTypesAndSpecials.get(guestCardType);
      for (int i = 0; i < specialsOfthisType.length; i++) {
        Card card = new Card();
        card.setCardType(CardType.SPECIAL);
        card.setGuestCardType(guestCardType);
        card.setImageName(specialsOfthisType[i]);
        card.setId(getCurrCardId());
        specials.add(card);
      }
    }
    return specials;
  }

  List<Card> createEnchantments() {
    return createCards(CardType.ENCHANT, CardNames.enchantNames);
  }

  List<Card> createUnwanteds() {
    return createCards(CardType.UNWANTED, CardNames.unwantedsNames);
  }

  List<Card> createDestroyCards() {
    return createCards(CardType.DESTROY, CardNames.destroyNames);
  }

  public List<Card> createDeck(int numPlayers) {
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

  List<Card> createEnchantPlayer() {
    return createCards(CardType.ENCHANT_PLAYER, CardNames.enchantPlayerNames);
  }

  List<Card> createCards(CardType cardType, String[] names) {
    List<Card> cards = new ArrayList<Card>();
    int numCardsPerType = numPerCardType.get(cardType);
    for (String name : names) {
      for (int i = 0; i < numCardsPerType; i++) {
        Card card = new Card();
        card.setCardType(cardType);
        card.setImageName(name + i);
        card.setId(getCurrCardId());
        cards.add(card);
      }
    }
    return cards;

  }

  @PostConstruct
  void main() {
    System.out.println("Creating deck");
    List<Card> deck = createDeck(3);
    Collections.shuffle(deck);
    for (Card card : deck) {
      // System.out.println(card.getImageName() + "(" + card.getId() + ")" + " = " + card.getCardType());
    }
    User newUser = new User();
    newUser.setName("John");
    User savedUser = userService.saveUser(newUser);
    User user = userService.getUserById(savedUser.getId());
    User userTwo = new User();
    userTwo.setName("Steve");
    User savedUserTwo = userService.saveUser(userTwo);
    User userThree = new User();
    userTwo.setName("AI");
    User savedUserThree = userService.saveUser(userThree);
    ///
    Game game = new Game(savedUser, savedUserTwo, savedUserThree);
    Game savedGame = gamesService.saveGame(game);
    Iterable<Game> userGames = gamesService.getUserGames(userTwo.getId());
    for (Game userGame : userGames) {
      System.out.println("User game id: " + userGame.getId());
      for(User userInGame : userGame.getUsers()) {
        System.out.println("User in game: " + userInGame.getName());
      }
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
    // player.getPlaces().getHand().setCards(createStartGast(3));;

    // player2.setName("steven");
    // players.add(player);
    // players.add(player2);
    // snapshot.setPlayers(players);
    // GameSnapshotJsonConverter converter = new GameSnapshotJsonConverter();
    // System.out.println(converter.convertToDatabaseColumn(snapshot));
  }

}