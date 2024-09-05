package com.wildeparty.model;

public class OutboundMessage {

  private MessageType type;
  private String content;
  private User sender;

  public OutboundMessage() {
    System.out.println("creating chat message");
  }

  public OutboundMessage(MessageType type, String content, User sender) {
    System.out.println("creating chat message");
    this.type = type;
    this.content = content;
    this.sender = sender;
  }

  public enum MessageType {
    CHAT,
    JOIN,
    LEAVE,
    STARTING_GAME,
  }

  public MessageType getType() {
    return type;
  }

  public void setType(MessageType type) {
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