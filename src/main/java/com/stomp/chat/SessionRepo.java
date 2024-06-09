package com.stomp.chat;

import java.util.Optional;

public class SessionRepo {
  Database db;

  SessionRepo(Database db) {
    this.db = db;
  }

  // List<Session> sessions = db.sessions;

  public int getUserIdFromSessionToken(String sessionToken) {

    Optional<Session> foundSession = db.sessions.stream().filter(e -> e.token.equals(sessionToken)).findFirst();
    System.out.println("found session associated with this cookie? " + foundSession.isPresent());
    return foundSession.isPresent() ? foundSession.get().userId : -1;
  }

  public void addSession(int userId, String token) {
    Session sesh = new Session(userId, token);
    db.sessions.add(sesh);
    System.out.println("after adding sesh:" + db.sessions.size());
  }

}
