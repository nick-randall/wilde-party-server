package com.wildeparty.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import com.wildeparty.model.Invitation;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
  @Query("SELECT i FROM Invitation i WHERE i.invitee.id = :userId OR i.inviter.id = :userId")
  List<Invitation> getUserInvitations(@Param("userId") Long inviteeId);
}
