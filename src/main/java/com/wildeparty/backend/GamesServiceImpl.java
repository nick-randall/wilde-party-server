package com.wildeparty.backend;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wildeparty.model.Game;
import com.wildeparty.model.GameStatus;

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
    return gamesRepository.findById(id).get();
  }

  @Override
  public List<Game> getUserActiveGames(Long userId) {
    List<Game> result = new ArrayList<Game>();
    Iterable<Game> iterable = gamesRepository.getUserGames(userId);
    for(Game game : iterable) {
      if (game.getStatus() != GameStatus.FINISHED || game.getStatus() != GameStatus.CANCELLED) {
        result.add(game);
      }
    }
    return result;
  }

  @Override
  public boolean isUserInGame(Long userId, Long gameId) {
    Iterable<Game> userGames = gamesRepository.getUserGames(userId);
    Iterator<Game> iterator = userGames.iterator();
    while (iterator.hasNext()) {
      Game game = iterator.next();
      if (game.getStatus() != GameStatus.FINISHED || game.getStatus() != GameStatus.CANCELLED) {
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
