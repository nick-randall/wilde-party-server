package com.stomp.chat;

import java.util.Optional;

import com.stomp.chat.model.Session;

public class SessionRepo {
  Database db;

  public SessionRepo(Database db) {
    this.db = db;
  }

  // List<Session> sessions = db.sessions;

  public Session getSessionFromSessionToken(String sessionToken) {

    Optional<Session> foundSession = db.sessions.stream().filter(e -> e.getToken().equals(sessionToken)).findFirst();
    System.out.println("found session associated with this cookie? " + foundSession.isPresent());
    return foundSession.isPresent() ? foundSession.get() : null;
  }

  public Session getSessionFromUserId(int userId) {
    Optional<Session> foundSession = db.sessions.stream().filter(e -> e.getUserId() == userId).findFirst();
    return foundSession.isPresent() ? foundSession.get() : null;

  }


  public void addSession(Long userId, String token) {
    Session sesh = new Session(userId, token);
    db.sessions.add(sesh);
    System.out.println("after adding sesh:" + db.sessions.size());
  }

}
