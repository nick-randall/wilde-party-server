package com.wildeparty.backend;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wildeparty.model.Game;
import com.wildeparty.model.GameStatus;
import com.wildeparty.model.User;

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
    for(Game game : iterable) {
    if (game.getStatus() != GameStatus.FINISHED && game.getStatus() !=
    GameStatus.CANCELLED) {
    result.add(game);
    }
    }
    return result;
  }

  @Override
  public boolean isUserInGame(User user, Long gameId) {
    List<Game> userGames = gamesRepository.findByUsers(user);
    Iterator<Game> iterator = userGames.iterator();
    while (iterator.hasNext()) {
    Game game = iterator.next();
    if (game.getStatus() != GameStatus.FINISHED && game.getStatus() !=
    GameStatus.CANCELLED) {
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
    throw new UnsupportedOperationException("Not implemented yet");
    // gamesRepository.save(game);
  }

}
