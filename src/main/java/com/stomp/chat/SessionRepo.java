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

  public void setIsInChatRoom(int sessionId, boolean isInChatRoom) {
    Optional<Session> session = db.sessions.stream().filter(e -> e.id == sessionId).findFirst();
    if (session.isPresent()) {
      db.sessions.remove(session.get());
      Session updated = session.get();
      updated.isInChatRoom = isInChatRoom;
      db.sessions.add(updated);
    }
  }

  public void addSession(int userId, String token, boolean isInChatRoom) {
    Session sesh = new Session(userId, token, isInChatRoom);
    db.sessions.add(sesh);
    System.out.println("after adding sesh:" + db.sessions.size());
  }

}
