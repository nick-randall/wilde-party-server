package com.wildeparty.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "sessions")
public class Session {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  Long id;
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  Long userId;
  String token;

  public Session() {
  }

  public Session(Long userId, String token) {
    this.userId = userId;
    this.token = token;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }


  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

}
