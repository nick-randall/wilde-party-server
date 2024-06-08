package com.stomp.chat;

public class UnnamedUser extends User {

  String token;

  UnnamedUser(String token) {
    super(null);
    this.token = token;
  }
  
}
