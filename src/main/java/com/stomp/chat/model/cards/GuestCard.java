package com.stomp.chat.model.cards;

import com.stomp.chat.model.LegalTargetType;
import com.stomp.chat.model.PlaceType;

public class GuestCard extends Card {

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
    return CardType.GUEST;
  }
  @Override
  PlaceType getLegalTargetPlaceType() {
    return PlaceType.GUEST_CARD_ZONE;
  }
  
}
