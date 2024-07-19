package com.stomp.chat;

import java.util.List;

import com.stomp.chat.model.Session;

import java.util.ArrayList;

public class Database {

  List<Session> sessions = new ArrayList<>();
  List<User> users = new ArrayList<>();

  public List<User> getUsers() {
    return users;
  }

  public List<Session> getSessions() {
    return sessions;
  }

  public void setSessions(List<Session> sessions) {
    this.sessions = sessions;
  }

  public void addUser(User user) {
    users.add(user);
  }
  
  public void setUsers(List<User> users) {
    this.users = users;
  }

  static private Database instance = null;

  private Database() {
  }

  static public Database getInstance() {
    if (instance == null) {
      instance = new Database();
    }
    return instance;
  }

}
