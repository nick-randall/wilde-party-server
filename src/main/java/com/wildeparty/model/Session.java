package com.wildeparty.model;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sessions")
public class Session {

  // static int currId = 0;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  Long id;
  Long userId;
  String token;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }


  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Session(Long userId, String token) {
    this.userId = userId;
    this.token = token;
  }

}
