package com.stomp.chat.model.cards;

import com.stomp.chat.model.LegalTargetType;
import com.stomp.chat.model.PlaceType;

public class UnwantedsCard extends Card {
    @Override
  CardActionType getCardActionType() {
    return CardActionType.ADD_DRAGGED;
  }
  @Override
  LegalTargetType getLegalTargetType() {
    return LegalTargetType.PLACE;
  }
  @Override 
  CardType getTargetCardType() {
    return CardType.UNWANTED;
  }
  @Override
  PlaceType getLegalTargetPlaceType() {
    return PlaceType.UNWANTEDS_ZONE;
  }
}
