package com.wildeparty.backend;

import com.wildeparty.model.Game;

public interface GamesService {

  public Game saveGame(Game game);

  public Game getGame(Long id);

  public void deleteGame(Long id);

  public void updateGame(Game game);

}
