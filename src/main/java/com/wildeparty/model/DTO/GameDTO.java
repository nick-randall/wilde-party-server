package com.wildeparty.model.DTO;
import java.util.List;
import java.util.ArrayList;

import com.wildeparty.model.User;
import com.wildeparty.model.gameElements.Game;
import com.wildeparty.model.gameElements.GameSnapshot;

public record GameDTO(Long id, List<PlayerDTO> players, GameSnapshot initialSnapshot) {
  public static GameDTO fromGame(Game game) {
    List<PlayerDTO> players = new ArrayList<PlayerDTO>();
    for(User player: game.getUsers()) {
      players.add(new PlayerDTO(player.getId(), player.getName(), player.isHuman()));
    }
    return new GameDTO(game.getId(), players, game.getGameSnapshots().get(0));

  }
}
