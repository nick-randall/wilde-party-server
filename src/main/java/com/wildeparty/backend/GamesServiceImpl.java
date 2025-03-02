package com.wildeparty.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wildeparty.model.User;
import com.wildeparty.model.gameElements.Game;
import com.wildeparty.model.gameElements.GameSnapshot;
import com.wildeparty.model.gameElements.GameStatus;

@Service
public class GamesServiceImpl implements GamesService {

  @Autowired
  GamesRepository gamesRepository;

  @Override
  public Game saveGame(Game game) {
    return gamesRepository.save(game);
  }

  @Override
  public Game getGame(Long id) {
    boolean exists = gamesRepository.existsById(id);
    if (exists) {
      Optional<Game> result = gamesRepository.findById(id);
      return result.orElse(null);
    }
    return null;
  }

  @Override
  public List<Game> getUserActiveGames(User user) {
    List<Game> result = new ArrayList<Game>();
    List<Game> iterable = gamesRepository.findByUsers(user);
    for (Game game : iterable) {
      if (game.getStatus() != GameStatus.FINISHED && game.getStatus() != GameStatus.CANCELLED) {
        result.add(game);
      }
    }
    return result;
  }

  public Game getUserActiveGame(User user) {
    List<Game> allUserGames = gamesRepository.findByUsers(user);
    List<Game> activeGames = allUserGames.stream()
        .filter(game -> game.getStatus() != GameStatus.FINISHED && game.getStatus() != GameStatus.CANCELLED).toList();
    if (activeGames.size() == 0) {
      return null;
    }
    if (activeGames.size() > 1) {
      throw new IllegalStateException("User is in more than one game");
    }
    return activeGames.get(0);
  }

  @Override
  public boolean isUserInGame(User user, Long gameId) {
    List<Game> allUserGames = gamesRepository.findByUsers(user);
    List<Game> activeGames = allUserGames.stream()
        .filter(game -> game.getStatus() != GameStatus.FINISHED && game.getStatus() != GameStatus.CANCELLED).toList();

    if (activeGames.size() > 1) {
      throw new IllegalStateException("User is in more than one game");
    }
    if (activeGames.size() == 0) {
      return false;
    }
    for (Game game : activeGames) {
      if (game.getId() == gameId) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void deleteGame(Long id) {
    gamesRepository.deleteById(id);
  }

  @Override
  public void updateGame(Game game) {
    Game existingGame = getGame(game.getId());
    if (existingGame == null) {
      throw new IllegalStateException("Game does not exist");
    }
    existingGame.setGameSnapshots(game.getGameSnapshots());
    existingGame.setUsers(game.getUsers());
    existingGame.setStatus(game.getStatus());
    gamesRepository.save(existingGame);
  }

  @Override
  public GameSnapshot addGameSnapshot(Long gameId, GameSnapshot snapshot) {
    Optional<Game> game = gamesRepository.findById(gameId);
    if(game.isPresent()) {
      Game existinggame = game.get();
      List<GameSnapshot> existingGameSnapshots = existinggame.getGameSnapshots();
      List<GameSnapshot> newSnapshots = new ArrayList<>();
      newSnapshots.addAll(existingGameSnapshots);
      // Required to avoid an error when saving the snapshot
      snapshot.setId(null);
      snapshot.resetActionResultsMap();
      newSnapshots.add(snapshot);
      existinggame.setGameSnapshots(newSnapshots);
      gamesRepository.save(existinggame);
      return existinggame.getLatestSnapshot();
    }
    return null;
  }

}
