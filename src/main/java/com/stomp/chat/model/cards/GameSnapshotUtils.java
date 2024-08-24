package com.stomp.chat.model.cards;

import com.stomp.chat.model.GameSnapshot;
import com.stomp.chat.model.Place;
import com.stomp.chat.model.Player;

public class GameSnapshotUtils {
  public boolean isCardProtected(GameSnapshot gameSnapshot, Card card) {
    Location location = findCard(card, gameSnapshot);
    Place GCZ = location.player.getPlaces().getGuestCardZone();
    Card cardToRight = GCZ.getCardToRight(location.index + 1);
    if (cardToRight == null) {
      return false;
    }
    return cardToRight.getCardType() == CardType.BFF || cardToRight.getCardType() == CardType.ENCHANT;
  }

  public Place findPlace(Place place, GameSnapshot gameSnapshot) {
    for (Player player : gameSnapshot.getPlayers()) {
      for (Place playerPlace : player.getPlaces().getAllPlaces()) {
        if (playerPlace == place) {
          return playerPlace;
        }
      }
    }
    for (Place gamePlace : gameSnapshot.allPlaces) {
      if (gamePlace == place) {
        return gamePlace;
      }
    }
    throw new RuntimeException("Place not found in gameSnapshot");
  }

  public Location findCard(Card card, GameSnapshot gameSnapshot) {

    for (Player player : gameSnapshot.getPlayers()) {
      for (Place place : player.getPlaces().getAllPlaces()) {
        for (int i = 0; i < place.getCards().size(); i++) {
          if (place.getCards().get(i) == card) {
            return new Location(player, place, i);
          }
        }
      }
    }
    for (Place place : gameSnapshot.allPlaces) {
      for (int i = 0; i < place.getCards().size(); i++) {
        if (place.getCards().get(i) == card) {
          return new Location(null, place, i);
        }
      }
    }
    throw new RuntimeException("Card not found in gameSnapshot");
  }
}

class Location {
  Player player;
  Place place;
  int index;

  public Location(Player player, Place place, int index) {
    this.player = player;
    this.place = place;
    this.index = index;
  }
}