package com.stomp.chat;

public class ChatMessage {

  private MessageType type;
  private String content;
  private User sender;

  public ChatMessage() {
    System.out.println("creating chat message");
  }

  public ChatMessage(MessageType type, String content, User sender) {
    System.out.println("creating chat message");
    this.type = type;
    this.content = content;
    this.sender = sender;
  }

  public enum MessageType {
    CHAT,
    JOIN,
    LEAVE
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