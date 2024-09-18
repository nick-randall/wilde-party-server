package com.wildeparty.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wildeparty.backend.Database;
import com.wildeparty.backend.GamesService;
import com.wildeparty.backend.SessionInMemoryRepo;
import com.wildeparty.backend.SessionService;
import com.wildeparty.backend.UserService;
import com.wildeparty.model.AddUserRequest;
import com.wildeparty.model.Game;
import com.wildeparty.model.Session;
import com.wildeparty.model.User;
import com.wildeparty.model.DTO.GameDTO;
import com.wildeparty.model.DTO.UserGameDTO;
import com.wildeparty.utils.GameSnapshotJsonConverter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.UUID;

@RestController
public class WhoAmIController {

  @Autowired
  UserService userService;
  @Autowired
  GamesService gamesService;
  @Autowired
  SessionService sessionService;

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
  public ResponseEntity<UserGameDTO> whoAmI(HttpServletResponse response, HttpServletRequest request) {
    try {
      Thread.sleep(1000);

    } catch (Exception e) {
      // TODO: handle exception
    }
    Cookie existingCookie = extractCookie(request);

    if (existingCookie == null) {
      // New user with no session cookie
      return ResponseEntity.noContent().build();
    }
    User user = userService.getUserBySessionToken(existingCookie.getValue());
    if(user == null) {
      return ResponseEntity.notFound().build();
    }
    List<Game> games = gamesService.getUserActiveGames(user);
    if(games.size() == 0) {
      return ResponseEntity.ok().body(new UserGameDTO(user, null));
    }

    if(games.size() > 1) {
      System.out.println("User " + user.getName() + " has more than one active game!!!!");
    }

    GameDTO gameDTO = GameDTO.fromGame(games.get(0));

    return ResponseEntity.ok().body(new UserGameDTO(user, gameDTO));
  }

  @PostMapping("/addUser")
  public ResponseEntity<User> addUser(HttpServletResponse response, HttpServletRequest request,
      @RequestBody AddUserRequest addUserRequest) {
    Cookie existingCookie = extractCookie(request);
    if(existingCookie != null) {
      User user = userService.getUserBySessionToken(existingCookie.getValue());
      if(user != null) {
        System.out.println("User already exists: " + user.getName());
        return ResponseEntity.ok().body(user);
      }
    }

    User newUser = userService.createUser(addUserRequest.getUsername());
    System.out.println("New user created: " + newUser.getName());
    Cookie newCookie = createCookie();
    Session session = sessionService.createSession(newUser.getId(), newCookie.getValue());
    newUser.setSession(session);
    userService.saveUser(newUser);
    System.out.println(session.getToken());
    System.out.println(session.getId());
    response.addCookie(newCookie);
    return ResponseEntity.ok().body(newUser);
  }

}
