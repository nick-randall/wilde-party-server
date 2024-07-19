package com.stomp.chat.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stomp.chat.model.Game;

public interface GamesRepository extends JpaRepository<Game, Long> {

}
