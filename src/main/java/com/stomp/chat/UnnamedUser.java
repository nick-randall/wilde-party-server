package com.stomp.chat;

public class UnnamedUser extends User {

  String token;

  UnnamedUser(String token) {
    super("unnamed", -1);
    this.token = token;
  }
  
}
