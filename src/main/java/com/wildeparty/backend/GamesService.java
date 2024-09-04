package com.wildeparty.backend;

import com.wildeparty.model.Game;

public interface GamesService {

  public Game saveGame(Game game);

  public Game getGame(Long id);

  public Iterable<Game> getUserGames(Long userId);

  public boolean isUserInGame(Long userId, Long gameId);

  public void deleteGame(Long id);

  public void updateGame(Game game);

}
