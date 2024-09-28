package com.wildeparty.backend;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wildeparty.model.User;
import com.wildeparty.model.DTO.UserGameDTO;
import com.wildeparty.model.gameElements.Game;
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
    for(Game game : activeGames) {
      if(game.getId() == gameId) {
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
