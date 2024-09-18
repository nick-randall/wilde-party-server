package com.wildeparty.model;

import java.util.Random;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
  public static User createAIUser() {
    Random rnd = new Random();
    User user = new User();
    String name = "AI" + rnd.nextInt(1000);
    user.setName(name);
    user.setHuman(false);
    return user;
  }
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  private String name;

  @OneToOne
  private Session session;

  // @ManyToMany(targetEntity = Game.class)
  // private List<Game> games = new ArrayList<Game>();
  private boolean isHuman = true;

  public User() {
  }

  public User(String name, Long id) {
    this.name = name;
    this.id = id;
  }

  public Session getSession() {
    return session;
  }


  public void setSession(Session session) {
    this.session = session;
  }

  // public List<Game> getGames() {
  //   return games;
  // }

  // public void setGames(List<Game> games) {
  //   this.games = games;
  // }

  // public void addGame(Game game) {
  //   games.add(game);
  // }

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

  public boolean isHuman() {
    return isHuman;
  }

  public void setHuman(boolean isHuman) {
    this.isHuman = isHuman;
  }
}
