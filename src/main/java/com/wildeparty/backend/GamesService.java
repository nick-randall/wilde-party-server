package com.wildeparty.backend;

import java.util.List;

import com.wildeparty.model.Game;

public interface GamesService {

  public Game saveGame(Game game);

  public Game getGame(Long id);

  public List<Game> getUserActiveGames(Long userId);

  public boolean isUserInGame(Long userId, Long gameId);

  public void deleteGame(Long id);

  public void updateGame(Game game);

}
