package com.wildeparty.model.cards;

import java.util.List;

import com.wildeparty.model.SnapshotUpdateData;
import com.wildeparty.model.SnapshotUpdateType;
import com.wildeparty.model.gameElements.GameEntity;
import com.wildeparty.model.gameElements.GameSnapshot;
import com.wildeparty.model.gameElements.LegalTargetType;
import com.wildeparty.model.gameElements.Player;
import com.wildeparty.model.gameElements.TargetPlayerType;

public class EnchantAction extends CardAction {
  GameSnapshotUtils utils = new GameSnapshotUtils();

  @Override
  public boolean isLegalTargetOf(GameSnapshot gameSnapshot, Card playedCard, GameEntity target) {
    if (!(target instanceof Card)) {
      return false;
    }
    Card targetCard = (Card) target;
    Location targetCardLocation = utils.findCard(targetCard, gameSnapshot);
    Player playedCardOwner = utils.findCard(playedCard, gameSnapshot).player;
    TargetPlayerType targetOwnerType = targetCardLocation.player == playedCardOwner ? TargetPlayerType.SELF
        : TargetPlayerType.ENEMY;
    boolean cardIsEnchant = playedCard.getCardType() == CardType.ENCHANT;
    boolean correctTargetOwnerType = playedCard.getLegalTargetOwnerType() == targetOwnerType;
    boolean correctTargetCardType = playedCard.getTargetCardType() == targetCard.getCardType();
    boolean cardAlreadyEnchanted = false;
    List<Card> cards = targetCardLocation.place.getCards();
    int targetCardIndex = cards.indexOf(targetCard);
    boolean checkLeft = targetCardIndex > 0;
    boolean checkRight = targetCardIndex < cards.size() - 1;
    if (checkLeft) {
      if (cards.get(targetCardIndex - 1).getCardType() == CardType.BFF) {
        cardAlreadyEnchanted = true;
      }
    }
    if (checkRight) {
      CardType rightNeighbourCardType = cards.get(targetCardIndex + 1).getCardType();
      if (rightNeighbourCardType == CardType.BFF || rightNeighbourCardType == CardType.ENCHANT) {
        cardAlreadyEnchanted = true;
      }
    }
    else {
      System.out.println("No right neighbour to check. targetCardIndex " + targetCardIndex + " > cards.size() - 1: " + (cards.size() - 1));
    }

    checker.setConditions(cardIsEnchant, correctTargetOwnerType, correctTargetCardType, !cardAlreadyEnchanted);
    return checker.areAllTrue();
  }

  @Override
  public CardActionResult getActionResult(GameSnapshot gameSnapshot, Card playedCard, GameEntity target) {
    if (isLegalTargetOf(gameSnapshot, playedCard, target)) {
      SnapshotUpdateData updateData = new SnapshotUpdateData(SnapshotUpdateType.ENCHANT,
          target.getId(), playedCard.getId());
      return new CardActionResult(target.getId(), true, LegalTargetType.CARD, updateData, CardActionType.ENCHANT);
    }
    return new CardActionResult(target.getId(), false);
  }

}
