package com.stomp.chat.model;

public class EnemyPlayer extends Player {
  public TargetPlayerType getTargetPlayerType() {
    return TargetPlayerType.ENEMY;
  }
}
