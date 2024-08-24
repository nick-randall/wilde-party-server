package com.wildeparty;

enum SessionResponse {
  FOUND_EXISTING_SESSION, REMOVED_EXPIRED_SESSION, CREATED_NEW_SESSION;

  String getName() {
    return "\"" + name() + "\"";
  }

}