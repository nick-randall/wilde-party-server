package com.wildeparty.model;

import com.fasterxml.jackson.annotation.JsonValue;

public class OutboundMessage {

  private PublicMessageType type;
  private String content;
  private User sender;

  public OutboundMessage() {
    System.out.println("creating chat message");
  }

  public OutboundMessage(PublicMessageType type, String content, User sender) {
    System.out.println("creating chat message");
    this.type = type;
    this.content = content;
    this.sender = sender;
  }

  public enum PublicMessageType {
    CHAT("chat"),
    JOIN("join"),
    LEAVE("leave"),
    STARTING_GAME("starting_game");

    private String name;

    @JsonValue
    public String getName() {
      return name;
    }

    PublicMessageType(String name) {
      this.name = name;
    }
  }

  public PublicMessageType getType() {
    return type;
  }

  public void setType(PublicMessageType type) {
    this.type = type;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public User getSender() {
    return sender;
  }

  public void setSender(User sender) {
    this.sender = sender;
  }
}