package com.wildeparty.model.DTO;

import com.wildeparty.model.Invitation;
import com.wildeparty.model.User;

import java.util.List;

public class OutboundInvitationMessage {

  private InvitationMessageType type;
  private User sender;

  private List<Invitation> userInvitations;

  public OutboundInvitationMessage(InvitationMessageType type) {
    this.type = type;
  }

  public InvitationMessageType getType() {
    return type;
  }

  public List<Invitation> getUserInvitations() {
    return userInvitations;
  }

  public void setType(InvitationMessageType type) {
    this.type = type;
  }

  public void setUserInvitations(List<Invitation> userInvitations) {
    this.userInvitations = userInvitations;
  }

  public User getSender() {
    return sender;
  }

  public void setSender(User sender) {
    this.sender = sender;
  }

}
