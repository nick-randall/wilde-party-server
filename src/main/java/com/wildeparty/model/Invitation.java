package com.wildeparty.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "invitations")
@Entity
public class Invitation {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  @ManyToOne
  private User inviter;
  @ManyToOne
  private User invitee;

  public Invitation() {
  }

  

  public Invitation(User inviter, User invitee) {
    this.inviter = inviter;
    this.invitee = invitee;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getInviter() {
    return inviter;
  }

  public void setInviter(User inviter) {
    this.inviter = inviter;
  }

  public User getInvitee() {
    return invitee;
  }

  public void setInvitee(User invitee) {
    this.invitee = invitee;
  }

}
