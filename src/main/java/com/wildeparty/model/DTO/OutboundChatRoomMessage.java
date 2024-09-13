package com.wildeparty.model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wildeparty.model.Invitation;

import java.util.List;

public class OutboundChatRoomMessage {

  private OutboundChatRoomMessageType type;
  private String message;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private GameDTO gameData;
  private List<Invitation> sentInvitations;
  private List<Invitation> receivedInvitations;

  public OutboundChatRoomMessage(OutboundChatRoomMessageType type) {
    this.type = type;
  }

  public OutboundChatRoomMessageType getType() {
    return type;
  }

  public List<Invitation> getSentInvitations() {
    return sentInvitations;
  }


  public List<Invitation> getReceivedInvitations() {
    return receivedInvitations;
  }

  public void setReceivedInvitations(List<Invitation> receivedInvitations) {
    this.receivedInvitations = receivedInvitations;
  }

  public void setType(OutboundChatRoomMessageType type) {
    this.type = type;
  }

  public void setSentInvitations(List<Invitation> sentInvitations) {
    this.sentInvitations = sentInvitations;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public GameDTO getGameData() {
    return gameData;
  }

  public void setGame(GameDTO gameData) {
    this.gameData = gameData;
  }

}
