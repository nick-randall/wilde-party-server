package com.stomp.chat;

import java.util.Optional;

public class SessionRepo {
  Database db;

  SessionRepo(Database db) {
    this.db = db;
  }

  // List<Session> sessions = db.sessions;

  public Session getSessionFromSessionToken(String sessionToken) {

    Optional<Session> foundSession = db.sessions.stream().filter(e -> e.token.equals(sessionToken)).findFirst();
    System.out.println("found session associated with this cookie? " + foundSession.isPresent());
    return foundSession.isPresent() ? foundSession.get() : null;
  }

  public Session getSessionFromUserId(int userId) {
    Optional<Session> foundSession = db.sessions.stream().filter(e -> e.userId == userId).findFirst();
    return foundSession.isPresent() ? foundSession.get() : null;

  }


  public void addSession(Long userId, String token) {
    Session sesh = new Session(userId, token);
    db.sessions.add(sesh);
    System.out.println("after adding sesh:" + db.sessions.size());
  }

}
