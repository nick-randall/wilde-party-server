package com.stomp.chat.backend;

import com.stomp.chat.model.Game;

public interface GamesService {

  public Game saveGame(Game game);

  public Game getGame(Long id);

  public void deleteGame(Long id);

  public void updateGame(Game game);

}
