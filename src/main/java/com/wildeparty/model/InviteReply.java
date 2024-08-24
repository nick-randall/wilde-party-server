package com.wildeparty.model;

public class InviteReply {

  private boolean accepted;

  public InviteReply(boolean accepted, int inviteId) {
    this.accepted = accepted;
    this.inviteId = inviteId;
  }

  public boolean isAccepted() {
    return accepted;
  }

  public void setAccepted(boolean accepted) {
    this.accepted = accepted;
  }

  private int inviteId;

  public int getInviteId() {
    return inviteId;
  }

  public void setInviteId(int inviteId) {
    this.inviteId = inviteId;
  }

}
