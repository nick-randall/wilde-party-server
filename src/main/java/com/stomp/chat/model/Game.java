package com.stomp.chat.model;

import com.stomp.chat.utils.GameSnapshotJsonConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "games")
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  public Long getId() {
    return id;
  }

  // @OneToOne(cascade=CascadeType.ALL)
  @Convert(converter = GameSnapshotJsonConverter.class)
  @Column(columnDefinition = "TEXT")
  private GameSnapshot gameSnapshot;

  public GameSnapshot getGameSnapshot() {
    return gameSnapshot;
  }

  public void setGameSnapshot(GameSnapshot gameSnapshot) {
    this.gameSnapshot = gameSnapshot;
  }

}
