package com.wildeparty.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wildeparty.model.Game;

public interface GamesRepository extends JpaRepository<Game, Long> {

}
