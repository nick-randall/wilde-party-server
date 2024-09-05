package com.wildeparty;

import com.wildeparty.model.Game;
import com.wildeparty.model.Session;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  Long id;
  String name;
  @OneToOne
  Session session;
  @ManyToMany(mappedBy = "users")
  private List<Game> games = new ArrayList<Game>();

  public User() {
  }

  User(String name, Long id) {
    this.name = name;
    this.id = id;
  }



  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

}
