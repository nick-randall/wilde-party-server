package com.stomp.chat;

enum SessionResponse {
  foundExistingSession, removedExpiredSession, createdNewSession;

  String getName() {
    return "\"" + name() + "\"";
  }

}