package com.stomp.chat;

import java.security.Principal;

public class User implements Principal {

  // static int currId = 0;

  int id;
  String name;

  User(String name, int id) {
  this.name = name;
  this.id = id;
  }

  @Override
  public String getName() {
    return name + id;
  }

  public String getUsername() {
    return name;
  }

  public int getId() {
    return id;
  }

}
