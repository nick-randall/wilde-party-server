package com.stomp.chat.model.cards;

import com.stomp.chat.model.GameSnapshot;

public interface CardAction {

  public boolean isLegalTargetOf(GameSnapshot gameSnapshot, Card playedCard, Object target);

  public CardActionResult getActionResult(GameSnapshot gameSnapshot, Card playedCard, Object target);

}