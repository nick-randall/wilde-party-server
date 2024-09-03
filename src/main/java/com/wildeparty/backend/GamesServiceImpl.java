package com.wildeparty.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wildeparty.model.Game;

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
  public Iterable<Game> getUserGames(Long userId) {
    return gamesRepository.getUserGames(userId);
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
