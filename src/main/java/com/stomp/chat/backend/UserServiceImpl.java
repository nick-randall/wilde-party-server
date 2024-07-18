package com.stomp.chat.backend;

import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stomp.chat.User;

import ch.qos.logback.classic.Logger;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  Logger logger = (Logger) LoggerFactory.getLogger(UserServiceImpl.class);

  @Override
  public User getUserById(Long userId) {
   Optional<User> user =  userRepository.findById(userId);
  return user.isPresent() ? user.get() : null;
  }

  @Override
  public User saveUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public List<User> fetchUserList() {
    return userRepository.findAll();
  }

  @Override
  public User updateUser(User user, Long userId) {
    Optional<User> possiblyUserToUpdate = userRepository.findById(userId);
    if (possiblyUserToUpdate.isPresent()) {
      User userToUpdate = possiblyUserToUpdate.get();
      userToUpdate.setName(user.getName());
      return userRepository.save(user);
    }
    // logger.error("User with id " + userId + " not found");
    return null;
  }

  @Override
  public void deleteUserById(Long UserId) {
    userRepository.deleteById(UserId);
  }

}
