package com.wildeparty.backend;

import java.util.List;

import com.wildeparty.model.User;

public interface UserService {

  User saveUser(User User);

  User createUser(String userName);

  User getUserBySessionToken(String token);

  List<User> fetchUserList();

  User updateUser(User User, Long UserId);

  void deleteUserById(Long UserId);

  User getUserById(Long UserId);
}