package com.wildeparty.model.gameElements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wildeparty.model.Current;
import com.wildeparty.model.SnapshotUpdateData;
import com.wildeparty.model.User;
import com.wildeparty.model.cards.Card;
import com.wildeparty.model.cards.CardActionResult;
import com.wildeparty.utils.CardActionResultsMapJsonConverter;
import com.wildeparty.utils.CurrentJsonConverter;
import com.wildeparty.utils.DeckCreator;
import com.wildeparty.utils.GameSnapshotJsonConverter;
import com.wildeparty.utils.NonPlayerPlacesJsonConverter;
import com.wildeparty.utils.PlayersListJsonConverter;
import com.wildeparty.utils.SnapshotUpdateDataJsonConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "game_snapshots")
public class GameSnapshot {

  public static GameSnapshot cloneGameSnapshot(GameSnapshot snapshot) {
    GameSnapshotJsonConverter converter = new GameSnapshotJsonConverter();
    String json = converter.convertToDatabaseColumn(snapshot);
    return converter.convertToEntityAttribute(json);
  }

  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Id
  private Long id;

  private int index;

  // @ManyToOne(targetEntity = Game.class)
  // Game game;

  @Column(columnDefinition = "TEXT")
  @Convert(converter = PlayersListJsonConverter.class)
  List<Player> players = new ArrayList<Player>();

  @Column(columnDefinition = "TEXT")
  @Convert(converter = CurrentJsonConverter.class)
  private Current current;

  @Column(columnDefinition = "TEXT")
  @Convert(converter = SnapshotUpdateDataJsonConverter.class)
  private SnapshotUpdateData snapshotUpdateData;

  @Column(columnDefinition = "TEXT")
  @Convert(converter = NonPlayerPlacesJsonConverter.class)
  private NonPlayerPlaces nonPlayerPlaces = new NonPlayerPlaces();

  // JsonInclude + Transient = no database persistence but still JSON serialization  
  @Transient
  @JsonProperty
  @Convert(converter = CardActionResultsMapJsonConverter.class)
  Map<Integer, List<CardActionResult>> actionResultsMap = new HashMap<Integer, List<CardActionResult>>();



  public GameSnapshot() {
  }

  public GameSnapshot(User userOne, User userTwo, User userThree) {
    this.index = 0;
    Player playerOne = new Player(userOne, 1);
    Player playerTwo = new Player(userTwo, 2);
    Player playerThree = new Player(userThree, 3);
    players.add(playerOne);
    players.add(playerTwo);
    players.add(playerThree);
    List<Card> deck = new DeckCreator().createDeck(3);
    getNonPlayerPlaces().getDeck().setCards(deck);
    current = Current.init();
  }

  public GameSnapshot(List<Player> players, Current current, NonPlayerPlaces nonPlayerPlaces,
      SnapshotUpdateData snapshotUpdateData) {
    this.players = players;
    this.current = current;
    this.snapshotUpdateData = snapshotUpdateData;
    this.nonPlayerPlaces = nonPlayerPlaces;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public GameSnapshot withUpdatedIndex() {
    // What about Non=Player-Places?
    GameSnapshot updatedGameSnapshot = this; // cloneGameSnapshot(this);
    updatedGameSnapshot.setIndex(this.index + 1);
    return updatedGameSnapshot;
  }

  // public GameSnapshot withCompletePhase() {
  //   GameSnapshot updatedGameSnapshot = this; // cloneGameSnapshot(this);
  //   Current current = updatedGameSnapshot.getCurrent();
  //   switch (current.getPhase()) {
  //     case DRAW_PHASE:
  //       current.setPhase(TurnPhase.PLAY_PHASE);
  //       break;
  //       case PLAY_PHASE:
  //       current.setPhase(TurnPhase.);
  //       break;
    
  //     default:
  //       break;
  //   }
  //   return updatedGameSnapshot;
  // }

  // public Map<Integer, List<CardActionResult>> getActionResultsMap() {
  //   return actionResultsMap;
  // }

  public void updateLegalTargets() {
    List<Card> currentHandCards = players.get(getCurrent().getPlayer()).getPlaces().getHand().getCards();
    System.out.println("Updating legal targets for " + currentHandCards.size() + " cards");
    for (Card card : currentHandCards) {
      List<CardActionResult> cardActionResults = new ArrayList<CardActionResult>();
      // Iterate through all players, places and cards to see if they are legal
      // targets.
      for (Player player : players) {
        player.gatherCardActionResults(this, card, cardActionResults);
      }
      this.actionResultsMap.put(card.getId(), cardActionResults);
    }
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public Current getCurrent() {
    return this.current;
  }

  public void setCurrent(Current current) {
    this.current = current;
  }

  public SnapshotUpdateData getSnapshotUpdateData() {
    return this.snapshotUpdateData;
  }

  public void setSnapshotUpdateData(SnapshotUpdateData snapshotUpdateData) {
    this.snapshotUpdateData = snapshotUpdateData;
  }

  public GameSnapshot players(List<Player> players) {
    setPlayers(players);
    return this;
  }

  public GameSnapshot current(Current current) {
    setCurrent(current);
    return this;
  }

  public GameSnapshot snapshotUpdateData(SnapshotUpdateData snapshotUpdateData) {
    setSnapshotUpdateData(snapshotUpdateData);
    return this;
  }

  @Override
  public String toString() {
    GameSnapshotJsonConverter converter = new GameSnapshotJsonConverter();
    return converter.convertToDatabaseColumn(this);
  }

  public List<Player> getPlayers() {
    return players;
  }

  public void setPlayers(List<Player> players) {
    this.players = players;
  }

  public NonPlayerPlaces getNonPlayerPlaces() {
    return nonPlayerPlaces;
  }

  public void setNonPlayerPlaces(NonPlayerPlaces nonPlayerPlaces) {
    this.nonPlayerPlaces = nonPlayerPlaces;
  }

}
