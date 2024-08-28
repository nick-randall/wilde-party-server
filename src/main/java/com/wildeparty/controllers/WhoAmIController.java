package com.wildeparty.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wildeparty.Database;
import com.wildeparty.SessionRepo;
import com.wildeparty.User;
import com.wildeparty.UserRepo;
import com.wildeparty.backend.GamesService;
import com.wildeparty.backend.UserService;
import com.wildeparty.model.AddUserRequest;
import com.wildeparty.model.Game;
import com.wildeparty.model.GameSnapshot;
import com.wildeparty.model.Player;
import com.wildeparty.model.Session;
import com.wildeparty.utils.GameSnapshotJsonConverter;
import com.wildeparty.utils.DeckCreator;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class WhoAmIController {

  final SessionRepo sessionRepo = new SessionRepo(Database.getInstance());
  final UserRepo userRepo = new UserRepo(Database.getInstance());
  @Autowired
  UserService userService;
  @Autowired
  GamesService gamesService;

  private Cookie createCookie() {

    System.out.println("No cookie");
    UUID uuid = UUID.randomUUID();
    Cookie cookie = new Cookie("SESSION", uuid.toString());
    return cookie;

  }

  private Cookie extractCookie(HttpServletRequest request) {
    Cookie[] existingCookies = request.getCookies();
    if (existingCookies != null) {
      if (existingCookies.length > 0) {
        return existingCookies[0];
      }
    }
    return null;
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(HttpServletResponse response, HttpServletRequest request) {
    System.out.println("logging out");
    Cookie existingCookie = extractCookie(request);
    if (existingCookie != null) {
      existingCookie.setMaxAge(0);
      response.addCookie(existingCookie);
    }
    return ResponseEntity.ok(null);
  }

  @PostMapping("/whoami")
  public ResponseEntity<User> whoAmI(HttpServletResponse response, HttpServletRequest request) {

    Cookie existingCookie = extractCookie(request);

    if (existingCookie == null) {
      // New user with no session cookie
      return ResponseEntity.noContent().build();
    }

    Session existingSession = sessionRepo.getSessionFromSessionToken(existingCookie.getValue());
    if (existingSession == null) {
      // Error -- user not found!
      return ResponseEntity.notFound().build();
    }
    User user = userRepo.getUserById(existingSession.getUserId());
    if (user == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().body(user);
  }

  @PostMapping("/addUser")
  public ResponseEntity<User> addUser(HttpServletResponse response, HttpServletRequest request,
      @RequestBody AddUserRequest addUserRequest) {
    User newUser = userRepo.createUser(addUserRequest.getUsername());
    Cookie newCookie = createCookie();
    sessionRepo.addSession(newUser.getId(), newCookie.getValue());
    response.addCookie(newCookie);

    /// Demo of UserServiceImpl
    User savedUser = userService.saveUser(newUser);
    User user = userService.getUserById(savedUser.getId());
    User userTwo = new User();
    userTwo.setName("Steve");
    User savedUserTwo = userService.saveUser(userTwo);
    User userThree = new User();
    userTwo.setName("AI");
    User savedUserThree = userService.saveUser(userThree);
    ///
    Game game = new Game(savedUser, savedUserTwo, savedUserThree);
    Game savedGame = gamesService.saveGame(game);
    gamesService.getGame(savedGame.getId());
    System.out.println(new GameSnapshotJsonConverter().convertToDatabaseColumn(savedGame.getGameSnapshot()));
    /// Demo of GamesServiceImpl

    return ResponseEntity.ok().body(newUser);
  }

}
