package com.stomp.chat.model;

import com.stomp.chat.User;
import com.stomp.chat.utils.GameSnapshotJsonConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "games")
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Convert(converter = GameSnapshotJsonConverter.class)
  @Column(columnDefinition = "TEXT")
  private GameSnapshot gameSnapshot;

  // @OneToMany(targetEntity = User.class)
  @ManyToMany(targetEntity = User.class)
  private List<User> users;

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
