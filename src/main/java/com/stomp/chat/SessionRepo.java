package com.stomp.chat;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class SessionRepo {

  List<Session> sessions = new ArrayList<>();

  public int getUserIdFromSessionToken(String sessionToken) {
    Optional<Session> foundSession = sessions.stream().filter(e -> e.token == sessionToken).findFirst();
    // if sessionId is expired, remove it.
    return foundSession.isPresent() ? foundSession.get().userId : -1;
  }

  public void addSession(int userId, String token) {
    Session sesh = new Session(userId, token);
    sessions.add(sesh);
  }

}
