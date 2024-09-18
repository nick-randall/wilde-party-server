package com.wildeparty.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wildeparty.backend.GamesService;
import com.wildeparty.backend.SessionService;
import com.wildeparty.backend.UserService;
import com.wildeparty.model.Game;
import com.wildeparty.model.GameSnapshot;
import com.wildeparty.model.Session;
import com.wildeparty.model.User;
import com.wildeparty.model.cards.Card;
import com.wildeparty.model.cards.SnapshotUpdater;

import jakarta.annotation.PostConstruct;

@RestController
public class JustGetSnapshotController {
  @Autowired
  UserService userService;
  @Autowired
  GamesService gamesService;
  @Autowired
  SessionService sessionService;

  @GetMapping("/just-get-snapshot")
  public ResponseEntity<GameSnapshot> justGetSnapshot() {
    System.out.println("justGetSnapshot");
    User newUser = new User();
    newUser.setName("John");
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
    SnapshotUpdater updater = new SnapshotUpdater(savedGame.getLatestSnapshot());
    GameSnapshot updatedSnapshot = updater.drawCards(game.getLatestSnapshot().getPlayers().get(0), 1);
    Card card = updatedSnapshot.getNonPlayerPlaces().getDeck().getCards().remove(0);
    updatedSnapshot.getPlayers().get(0).getPlaces().getGuestCardZone().getCards().add(card);

    // GameSnapshotJsonConverter converter = new GameSnapshotJsonConverter();
    // String snapshotJson =
    // converter.convertToDatabaseColumn(savedGame.getGameSnapshot());
    System.out.println("Game snapshot: " + updatedSnapshot);
    return ResponseEntity.ok().body(updatedSnapshot);
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
      User userThree =  User.createAIUser();
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
      // List<GameSnapshot> snapshots = SnapshotSetupUtil.setupInitialGameSnapshots(initialSnapshot);
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
      List<Game> games  = gamesService.getUserActiveGames(savedUser);
      String x = "";
    

    }


}
