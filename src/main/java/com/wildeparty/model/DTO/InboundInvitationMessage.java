package com.wildeparty.model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;



public class InboundInvitationMessage {
  private InvitationMessageType type; 
  private Long inviteeId;
  private Long invitationId;

  public InvitationMessageType getType() {
    return type;
  }

  public void setType(InvitationMessageType type) {
    this.type = type;
  }

  @JsonInclude(JsonInclude.Include.NON_NULL)
  public Long getInviteeId() {
    return inviteeId;
  }

  public void setInviteeId(Long inviteeId) {
    this.inviteeId = inviteeId;
  }

  @JsonInclude(JsonInclude.Include.NON_NULL)
  public Long getInvitationId() {
    return invitationId;
  }

  public void setInvitationId(Long invitationId) {
    this.invitationId = invitationId;
    
  }
}

