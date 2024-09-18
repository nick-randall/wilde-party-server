package com.wildeparty.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wildeparty.model.Game;
import com.wildeparty.model.User;

import java.util.List;

public interface GamesRepository extends JpaRepository<Game, Long> {
  // @Query("SELECT g FROM Game g JOIN games_users gu ON gu.game_id = g.id WHERE gu.users_id = :userId")
  public List<Game> findByUsers(User user);
  

}
