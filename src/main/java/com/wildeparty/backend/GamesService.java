package com.wildeparty.backend;

import java.util.List;

import com.wildeparty.model.User;
import com.wildeparty.model.gameElements.Game;
import com.wildeparty.model.gameElements.GameSnapshot;

public interface GamesService {

  public Game saveGame(Game game);

  public Game getGame(Long id);

  public List<Game> getUserActiveGames(User user);

  public boolean isUserInGame(User user, Long gameId);

  public void deleteGame(Long id);

  public void updateGame(Game game);

  public GameSnapshot addGameSnapshot(Long gameId, GameSnapshot snapshot);

}
