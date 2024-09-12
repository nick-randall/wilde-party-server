package com.wildeparty.model;

import com.wildeparty.utils.GameSnapshotJsonConverter;
import com.wildeparty.utils.SnapshotSetupUtil;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "games")
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Convert(converter = GameSnapshotJsonConverter.class)
  @Column(columnDefinition = "TEXT")
  private List<GameSnapshot> gameSnapshots = new ArrayList<>();

  @ManyToMany(fetch = FetchType.EAGER)
  private List<User> users = new ArrayList<>();
  private GameStatus status = GameStatus.CREATED;
  @OneToOne(targetEntity = User.class)
  private User winner;

  public Game() {
  }

  public Game(User userOne, User userTwo, User userThree) {
    this.users.add(userOne);
    this.users.add(userTwo);
    this.users.add(userThree);
    GameSnapshot initialSnapshot = new GameSnapshot(userOne, userTwo, userThree);
    List<GameSnapshot> initialSnapshots = SnapshotSetupUtil.setupInitialGameSnapshots(initialSnapshot);
    this.gameSnapshots.addAll(initialSnapshots);

  }

  public User getWinner() {
    return winner;
  }

  public void setWinner(User winner) {
    this.winner = winner;
  }

  public GameStatus getStatus() {
    return status;
  }

  public void setStatus(GameStatus status) {
    this.status = status;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

  public Long getId() {
    return id;
  }

  public List<GameSnapshot> getGameSnapshots() {
    return gameSnapshots;
  }

  public GameSnapshot getLatestSnapshot() {
    return gameSnapshots.get(gameSnapshots.size() - 1);
  }

  public void setGameSnapshot(List<GameSnapshot> gameSnapshots) {
    this.gameSnapshots = gameSnapshots;
  }

}
