package com.stomp.chat;

public class User {

  int id;
  String name;

  User(String name, int id) {
  this.name = name;
  this.id = id;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

}
