package com.stomp.chat.model.cards;

import com.stomp.chat.model.LegalTargetType;
import com.stomp.chat.model.PlaceType;
import com.stomp.chat.model.TargetPlayerType;

public class Action {
  private PlaceType targetPlaceType;
  private CardType targetCardType;
  private CardActionType cardActionType;
  private LegalTargetType legalTargetType;
  private TargetPlayerType targetOwnerType;

  public PlaceType getTargetPlaceType() {
    return targetPlaceType;
  }

  public void setTargetPlaceType(PlaceType targetPlaceType) {
    this.targetPlaceType = targetPlaceType;
  }

  public CardType getTargetCardType() {
    return targetCardType;
  }

  public void setTargetCardType(CardType targetCardType) {
    this.targetCardType = targetCardType;
  }

  public CardActionType getCardActionType() {
    return cardActionType;
  }

  public void setCardActionType(CardActionType cardActionType) {
    this.cardActionType = cardActionType;
  }

  public LegalTargetType getLegalTargetType() {
    return legalTargetType;
  }

  public void setLegalTargetType(LegalTargetType legalTargetType) {
    this.legalTargetType = legalTargetType;
  }

  public TargetPlayerType getTargetOwnerType() {
    return targetOwnerType;
  }

  public void setTargetOwnerType(TargetPlayerType targetOwnerType) {
    this.targetOwnerType = targetOwnerType;
  }

  public Action(PlaceType targetPlaceType, CardType targetCardType, CardActionType cardActionType,
      LegalTargetType legalTargetType, TargetPlayerType targetOwnerType) {
    this.targetPlaceType = targetPlaceType;
    this.targetCardType = targetCardType;
    this.cardActionType = cardActionType;
    this.legalTargetType = legalTargetType;
    this.targetOwnerType = targetOwnerType;
  }

  static public Action fromCard(Card card) {
    return new Action(card.getLegalTargetPlaceType(),
        card.getTargetCardType(),
        card.getCardActionType(),
        card.getLegalTargetType(),
        card.getLegalTargetOwnerType());
  }

}
