package com.wildeparty.model;

import com.wildeparty.utils.GameSnapshotJsonConverter;

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
  private GameSnapshot gameSnapshot;

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
    this.gameSnapshot = new GameSnapshot(userOne, userTwo, userThree);
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

  public GameSnapshot getGameSnapshot() {
    return gameSnapshot;
  }

  public void setGameSnapshot(GameSnapshot gameSnapshot) {
    this.gameSnapshot = gameSnapshot;
  }

}
