package com.wildeparty;

import java.util.Optional;

public class UserInMemoryRepo {

  static Long currUserId = 0L;

  Database db;

  public UserInMemoryRepo(Database db) {
    this.db = db;
  }

  public User getUserById(Long userId) {
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
