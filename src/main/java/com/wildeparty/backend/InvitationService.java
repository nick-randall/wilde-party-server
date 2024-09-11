package com.wildeparty.backend;

import java.util.List;
import com.wildeparty.model.Invitation;

abstract public class InvitationService {

  abstract public List<Invitation> getUserInvitations(Long userId);

  abstract public List<Invitation> getReceivedInvitationsByUserId(Long userId);

  abstract public List<Invitation> getSentInvitationsByUserId(Long userId);

  abstract public Invitation saveInvitation(Invitation invitation);

  abstract public void deleteInvitation(Long invitationId);

  abstract public void deleteAllUserInvitations(Long userId);

  abstract public Invitation getInvitationById(Long id);

}
