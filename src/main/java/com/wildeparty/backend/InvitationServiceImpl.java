package com.wildeparty.backend;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wildeparty.model.Invitation;

@Service
public class InvitationServiceImpl extends InvitationService {
  @Autowired
  InvitationRepository repository;;

  @Override
  public List<Invitation> getUserInvitations(Long userId) {
    return repository.getUserInvitations(userId);
  }

  @Override
  public Invitation saveInvitation(Invitation invitation) {
    return repository.save(invitation);
  }

  @Override
  public void deleteInvitation(Long invitationId) {
    repository.deleteById(invitationId);
  }

  @Override
  public Invitation getInvitationById(Long id) {
   return repository.findById(id).orElse(null);
  }

  @Override
  public List<Invitation> getReceivedInvitationsByUserId(Long userId) {
    return repository.getUserReceivedInvitations(userId);

  }

  @Override
  public List<Invitation> getSentInvitationsByUserId(Long userId) {
    return repository.getUserSentInvitations(userId);

  }

  @Override
  public void deleteAllUserInvitations(Long userId) {
    repository.deleteAllUserInvitations(userId);
  }
}
