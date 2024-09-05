package com.wildeparty.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wildeparty.model.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {
  
}
