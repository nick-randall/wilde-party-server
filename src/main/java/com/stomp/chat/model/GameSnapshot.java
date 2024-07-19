package com.stomp.chat.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class GameSnapshot implements Serializable{

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  public Long getId() {
    return id;
  }
  @Column
  List<String> players;

  public List<String> getPlayers() {
    return players;
  }

  public void setPlayers(List<String> players) {
    this.players = players;
  }

}
