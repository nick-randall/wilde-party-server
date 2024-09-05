package com.wildeparty.backend;

import java.util.List;

import com.wildeparty.model.Session;

public abstract class SessionService {
  
  public abstract List<Session> getSessions();

  public abstract Session getSessionById(Long id);

  public abstract Session createSession(Long userId, String token);

  public abstract void deleteSession(Long id);

}
