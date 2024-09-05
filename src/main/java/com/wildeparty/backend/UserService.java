package com.wildeparty.backend;

import java.util.List;

import com.wildeparty.model.User;

public interface UserService {

  // save operation
  User saveUser(User User);

  User createUser(String userName);

  User getUserBySessionToken(String token);

  // read operation
  List<User> fetchUserList();

  // update operation
  User updateUser(User User, Long UserId);

  // delete operation
  void deleteUserById(Long UserId);

  User getUserById(Long UserId);
}