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
    boolean correctTargetOwnerType = playedCard.getLegalTargetOwnerType() == targetOwnerType;
    boolean correctTargetCardType = playedCard.getTargetCardType() == targetCard.getCardType();
    System.out.println("Enchant card: " + targetCard.getName() + " Played Card: " + playedCard.getName()
        + " correct Owner type (" + correctTargetOwnerType + ")");
    System.out.println("Played Card Target Type = " + playedCard.getTargetCardType() + ", Target Card Type = " + targetCard.getCardType() + "... Correct Target Card type? (" + correctTargetCardType + ")");
    // Check if BFF to left or any other enchantment to right
    boolean cardAlreadyEnchanted = false;
    List<Card> cards = targetCardLocation.place.getCards();
    int targetCardIndex = cards.indexOf(targetCard);
    boolean checkLeft = targetCardIndex > 0;
    boolean checkRight = targetCardIndex > cards.size() - 1;
    if (checkLeft) {
      System.out.println("Checking left...");
      if (cards.get(targetCardIndex - 1).getCardType() == CardType.BFF) {
        cardAlreadyEnchanted = true;
      }
      System.out.println("Card already enchantted ? " + cardAlreadyEnchanted);
    }
    if (checkRight) {
      System.out.println("Checking right...");
      CardType rightNeighbourCardType = cards.get(targetCardIndex + 1).getCardType();
      if (rightNeighbourCardType == CardType.BFF || rightNeighbourCardType == CardType.ENCHANT) {
        cardAlreadyEnchanted = true;
      }
      System.out.println("Card already enchantted ? " + cardAlreadyEnchanted);

    }
    System.out.println("Card already enchanted (At final count) ? " + cardAlreadyEnchanted);

    checker.setConditions(correctTargetOwnerType, correctTargetCardType, !cardAlreadyEnchanted);
    System.out.println(" All conditions passed ? " + checker.areAllTrue());
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
