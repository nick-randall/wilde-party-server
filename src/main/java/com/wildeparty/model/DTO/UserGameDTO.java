package com.wildeparty.model.DTO;

import com.wildeparty.model.User;

public record UserGameDTO(User user, GameDTO gameData) {
  
}
