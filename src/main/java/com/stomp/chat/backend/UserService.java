package com.stomp.chat.backend;

import java.util.List;

import com.stomp.chat.User;

public interface UserService {

  // save operation
  User saveUser(User User);

  // read operation
  List<User> fetchUserList();

  // update operation
  User updateUser(User User, Long UserId);

  // delete operation
  void deleteUserById(Long UserId);

  User getUserById(Long UserId);
}