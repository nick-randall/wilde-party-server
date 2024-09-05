package com.wildeparty.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wildeparty.model.Game;

public interface GamesRepository extends JpaRepository<Game, Long> {
  @Query("SELECT g FROM Game g JOIN g.users u WHERE u.id = :userId")
  public Iterable<Game> getUserGames(@Param("userId") Long userId);

}
