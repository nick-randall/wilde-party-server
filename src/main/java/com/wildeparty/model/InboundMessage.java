package com.wildeparty.model;

import com.wildeparty.model.OutboundMessage.PublicMessageType;

public class InboundMessage {
  private String username;
  private PublicMessageType type;
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

  public PublicMessageType getType() {
    return type;
  }

  public void setType(PublicMessageType type) {
    this.type = type;
  }
}