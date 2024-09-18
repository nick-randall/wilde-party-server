package com.wildeparty.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wildeparty.model.cards.Card;
import com.wildeparty.model.cards.CardActionResult;
import com.wildeparty.model.cards.SnapshotUpdater;
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
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "game_snapshots")
public class GameSnapshot {

  @Id
  private int id;

  @ManyToOne(targetEntity = Game.class)
  Game game;

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

  @Column(columnDefinition = "TEXT")
  @Convert(converter = CardActionResultsMapJsonConverter.class)
  Map<Integer, List<CardActionResult>> actionResultsMap = new HashMap<Integer, List<CardActionResult>>();

  public GameSnapshot() {
  }

  public GameSnapshot(User userOne, User userTwo, User userThree) {
    this.id = 0;
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
    // What about Non=Player-Places?

    this.players = players;
    this.current = current;
    this.snapshotUpdateData = snapshotUpdateData;
    this.nonPlayerPlaces = nonPlayerPlaces;
  }

  public GameSnapshot withUpdatedId() {
    // What about Non=Player-Places?
    GameSnapshot updatedGameSnapshot = this; // cloneGameSnapshot(this);
    updatedGameSnapshot.setId(this.id + 1);
    return updatedGameSnapshot;
  }

  public void updateLegalTargets() {
    List<Card> currentHandCards = players.get(getCurrent().getPlayer()).getPlaces().getHand().getCards();
    SnapshotUpdater updater = new SnapshotUpdater(this);
    // All legal targets will be calculated based on player having drawn once.
    updater.drawCard(players.get(getCurrent().getPlayer()));
    for (Card card : currentHandCards) {
      List<CardActionResult> cardActionResults = new ArrayList<CardActionResult>();
      // Iterate through all players, places and cards to see if they are legal
      // targets.
      for (Player player : players) {
        player.gatherCardActionResults(this, card, cardActionResults);
      }
      actionResultsMap.put(card.getId(), cardActionResults);
    }
  }

  public static GameSnapshot cloneGameSnapshot(GameSnapshot snapshot) {
    GameSnapshotJsonConverter converter = new GameSnapshotJsonConverter();
    String json = converter.convertToDatabaseColumn(snapshot);
    return converter.convertToEntityAttribute(json);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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
