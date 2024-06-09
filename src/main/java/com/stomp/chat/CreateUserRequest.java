package com.stomp.chat;

class CreateUserRequest {
 private String username;
  private MessageType type;
 

  public enum MessageType {
    CHAT,
    JOIN,
    LEAVE
  }


  public String getUsername() {
    return username;
  }


  public void setUsername(String username) {
    this.username = username;
  }


  public MessageType getType() {
    return type;
  }


  public void setType(MessageType type) {
    this.type = type;
  }
}