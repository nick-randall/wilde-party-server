package com.stomp.chat;

import com.stomp.chat.OutboundMessage.MessageType;

class InboundMessage {
  private String username;
  private MessageType type;
  private String content;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
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