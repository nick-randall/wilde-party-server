package com.stomp.chat;

public class Session {

  static int currId = 0;

  int id;
  int userId;
  String token;

  public Session(int userId, String token) {
    this.userId = userId;
    this.token = token;
  }

}
