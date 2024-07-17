package com.stomp.chat;

import jakarta.annotation.Generated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "sessions")
public class Session {

  // static int currId = 0;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  int id;
  int userId;
  String token;

  public Session(int userId, String token) {
    this.userId = userId;
    this.token = token;
  }

}
