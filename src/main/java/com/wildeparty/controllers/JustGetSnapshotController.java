package com.wildeparty.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wildeparty.backend.GamesService;
import com.wildeparty.backend.SessionService;
import com.wildeparty.backend.UserService;
import com.wildeparty.model.Session;
import com.wildeparty.model.User;
import com.wildeparty.model.DTO.GameDTO;
import com.wildeparty.model.DTO.UserGameDTO;
import com.wildeparty.model.cards.Card;
import com.wildeparty.model.cards.SnapshotUpdater;
import com.wildeparty.model.gameElements.Game;
import com.wildeparty.model.gameElements.GameSnapshot;
import com.wildeparty.utils.SnapshotSetupUtil;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class JustGetSnapshotController {
  @Autowired
  UserService userService;
  @Autowired
  GamesService gamesService;
  @Autowired
  SessionService sessionService;

  private Cookie extractCookie(HttpServletRequest request) {
    Cookie[] existingCookies = request.getCookies();
    if (existingCookies != null) {
      if (existingCookies.length > 0) {
        return existingCookies[0];
      }
    }
    return null;
  }

  private Cookie createCookie() {
    UUID uuid = UUID.randomUUID();
    Cookie cookie = new Cookie("SESSION", uuid.toString());
    return cookie;

  }

  @PostMapping("/just-create-game")
  public ResponseEntity<UserGameDTO> justCreateGame(HttpServletResponse response, HttpServletRequest request) {
    System.out.println("just create demo game called");
    User currUser = null;
    Cookie cookie = extractCookie(request);
    if (cookie != null) {
      currUser = userService.getUserBySessionToken(cookie.getValue());
    }

    if (currUser == null) {
      currUser = userService.createUser("Demo Roger");
      System.out.println("New user created: " + currUser.getName());
      Cookie newCookie = createCookie();
      Session session = sessionService.createSession(currUser.getId(), newCookie.getValue());
      currUser.setSession(session);
      userService.saveUser(currUser);
      response.addCookie(newCookie);
    }

    User userTwo = User.createAIUser();
    User savedUserTwo = userService.saveUser(userTwo);
    User userThree = User.createAIUser();
    User savedUserThree = userService.saveUser(userThree);
    ///
    Game game = new Game(currUser, savedUserTwo, savedUserThree);
    Game savedGame = gamesService.saveGame(game);
    SnapshotSetupUtil.setupInitialGameSnapshots(savedGame.getLatestSnapshot());
    GameDTO gameDTO = GameDTO.fromGame(game);

    return ResponseEntity.ok().body(new UserGameDTO(currUser, gameDTO));

  }

  @PostMapping("/end-game")
  public ResponseEntity<Void> endCurrentGame(HttpServletRequest request) {
    Cookie cookie = extractCookie(request);
    if (cookie == null) {
      return ResponseEntity.notFound().build();
    }
    User user = userService.getUserBySessionToken(cookie.getValue());
    if (user == null) {
      return ResponseEntity.notFound().build();
    }
    List<Game> userGames = gamesService.getUserActiveGames(user);
    gamesService.deleteGame(userGames.get(0).getId());
    return ResponseEntity.ok(null);
  }

  @GetMapping("game-latest")
  @ResponseBody
  public GameSnapshot getLatestSnapshot(@RequestParam("id") String gameId) {
    System.out.println("getLatestSnapshot for game " + gameId);
    return gamesService.getGame(Long.parseLong(gameId)).getLatestSnapshot();
  }

  @PostConstruct
  void main() {
    System.out.println("Creating game with snapshots");
    User newUser = new User();
    newUser.setName("John");
    User userTwo = new User();
    userTwo.setName("Steve");
    User userThree = User.createAIUser();
    User savedUser = userService.saveUser(newUser);
    User savedUserTwo = userService.saveUser(userTwo);
    User savedUserThree = userService.saveUser(userThree);

    Session userSession = sessionService.createSession(savedUser.getId(), "TOKAEN");
    Session userSessionTwo = sessionService.createSession(savedUser.getId(), "TOKAEN");
    Session userSessionThree = sessionService.createSession(savedUser.getId(), "TOKENEad");
    savedUser.setSession(userSession);
    savedUserTwo.setSession(userSessionTwo);
    savedUserThree.setSession(userSessionThree);
    userService.saveUser(savedUser);
    userService.saveUser(savedUserTwo);
    userService.saveUser(savedUserThree);
    ///

    Game game = new Game(savedUser, savedUserTwo, savedUserThree);
    // game.setWinner(null);
    // GameSnapshot initialSnapshot = new GameSnapshot();
    // List<GameSnapshot> snapshots =
    // SnapshotSetupUtil.setupInitialGameSnapshots(initialSnapshot);
    // game.setGameSnapshots(snapshots);

    ////
    // User loadedUserOne = userService.getUserById(savedUser.getId());
    // User loadedUserTwo = userService.getUserById(savedUserTwo.getId());
    // User loadedUserThree = userService.getUserById(savedUserThree.getId());
    // List<User> users = new ArrayList<>();
    // users.add(loadedUserOne);
    // users.add(loadedUserTwo);
    // users.add(loadedUserThree);
    // game.setUsers(users);

    ///
    Game savedGame = gamesService.saveGame(game);
    System.out.println("Game saved with id " + savedGame.getId());
    Game o = gamesService.getGame(savedGame.getId());
    List<Game> games = gamesService.getUserActiveGames(savedUser);
    String x = "";

  }

}
