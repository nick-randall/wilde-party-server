package com.stomp.chat;

import java.util.Optional;

public class UserRepo {

  static int currUserId = 0;

  Database db;

  UserRepo(Database db) {
    this.db = db;
  }

  public User getUserById(int userId) {
    Optional<User> foundUser = db.users.stream().filter(e -> e.id == userId).findFirst();
    // if sessionId is expired, remove it.
    return foundUser.isPresent() ? foundUser.get(): null;
  }

  public User createUser(String username) {
    System.out.println("add user in database: " + username);
    User user = new User(username, currUserId);
    currUserId ++;
    db.users.add(user);
    return user;
  }

}
