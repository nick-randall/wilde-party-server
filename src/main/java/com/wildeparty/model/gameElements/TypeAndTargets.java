package com.wildeparty.model.gameElements;

import java.util.List;
import java.util.ArrayList;

public class TypeAndTargets {
  private LegalTargetType legalTargetType;
  private List<Integer> targetCardIds = new ArrayList<Integer>();
  
  public LegalTargetType getLegalTargetType() {
    return legalTargetType;
  }

  public void setLegalTargetType(LegalTargetType legalTargetType) {
    this.legalTargetType = legalTargetType;
  }

  public List<Integer> getTargetCardIds() {
    return targetCardIds;
  }

  public void setTargetCardIds(List<Integer> targetCardIds) {
    this.targetCardIds = targetCardIds;
  }

  public TypeAndTargets(LegalTargetType legalTargetType) {
    this.legalTargetType = legalTargetType;
  }
  
}
