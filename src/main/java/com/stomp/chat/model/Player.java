package com.stomp.chat.model;

import java.io.Serializable;

import com.stomp.chat.User;

public class Player implements Serializable {
  private Long id;

  private Long userId;

  private String name;

  private PlayerPlaces places = new PlayerPlaces();

  private boolean underProtection = false;

  public Player() {

  }

  public Player(User user) {
    this.userId = user.getId();
    this.name = user.getName();
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public PlayerPlaces getPlaces() {
    return places;
  }

  public void setPlaces(PlayerPlaces places) {
    this.places = places;
  }

  public boolean isUnderProtection() {
    return underProtection;
  }

  public void setUnderProtection(boolean underProtection) {
    this.underProtection = underProtection;
  }
}
