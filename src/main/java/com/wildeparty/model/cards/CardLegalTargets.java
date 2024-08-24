package com.wildeparty.model.cards;

import com.wildeparty.model.LegalTargetType;

public class CardLegalTargets {

  private int[] legalTargets;
  private LegalTargetType legalTargetType;

  public int[] getLegalTargets() {
    return legalTargets;
  }

  public void setLegalTargets(int[] legalTargets) {
    this.legalTargets = legalTargets;
  }

  public LegalTargetType getLegalTargetType() {
    return legalTargetType;
  }

  public void setLegalTargetType(LegalTargetType legalTargetType) {
    this.legalTargetType = legalTargetType;
  }
}
