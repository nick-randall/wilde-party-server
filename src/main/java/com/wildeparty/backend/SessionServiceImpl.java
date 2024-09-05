package com.wildeparty.backend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wildeparty.model.Session;

@Service
public class SessionServiceImpl extends SessionService {
  
  @Autowired
  private SessionRepository sessionRepository;

  @Override
  public List<Session> getSessions() {
    return sessionRepository.findAll();
  }

  @Override
  public Session getSessionById(Long id) {
    return sessionRepository.findById(id).orElse(null);
  }

  @Override
  public Session createSession(Long userId, String token) {
    Session session = new Session(userId, token);
    return sessionRepository.save(session);
  }

  @Override
  public void deleteSession(Long id) {
    sessionRepository.deleteById(id);
  }


}
