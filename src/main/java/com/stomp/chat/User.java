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

  // User(String name) {
  //   System.out.println("creating new user " + currId);
  //   // StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
  //   // for(StackTraceElement s: stackTraceElements) {
  //   //   System.out.println(s);
  //   // }
  //   this.name = name;
  //   this.id = currId;
  //   currId++;
  // }

  @Override
  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

}
