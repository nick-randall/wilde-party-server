package com.stomp.chat.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stomp.chat.utils.GameSnapshotJsonConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// @Entity
public class GameSnapshot implements Serializable {

  // @Id
  // @GeneratedValue(strategy = GenerationType.SEQUENCE)
  // private Long id;

  // public Long getId() {
  //   return id;
  // }

  // @Convert(converter = GameSnapshotJsonConverter.class)
  List<Player> players;
  

  public List<Player> getPlayers() {
    return players;
  }

  public void setPlayers(List<Player> players) {
    this.players = players;
  }

}
