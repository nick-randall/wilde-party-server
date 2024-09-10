package com.wildeparty.controllers;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wildeparty.backend.GamesService;
import com.wildeparty.backend.InvitationService;
import com.wildeparty.backend.SessionService;
import com.wildeparty.backend.UserService;
import com.wildeparty.model.OutboundMessage;
import com.wildeparty.model.User;
import com.wildeparty.model.DTO.InboundInvitationMessage;
import com.wildeparty.model.DTO.InvitationMessageType;
import com.wildeparty.model.DTO.OutboundInvitationMessage;
import com.wildeparty.model.OutboundMessage.PublicMessageType;
import com.wildeparty.model.Game;
import com.wildeparty.model.InboundMessage;
import com.wildeparty.model.Invitation;

@Controller
public class ChatController {

  @Autowired
  private GamesService gamesService;
  @Autowired
  private UserService userService;
  @Autowired
  private InvitationService invitationService;
  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;
  @Autowired
  private SimpUserRegistry simpUserRegistry;

  private User getSender(SimpMessageHeaderAccessor sha) {
    // In WebsocketConfig, we set the userId as the Principal name
    // Normally, the Principal getName() method returns the username
    // which, if it were an email address, would be unique.
    // Since we are using an integer userId, we have set it as the Principal
    // getName()
    // value.
    Long userId = Long.parseLong(sha.getUser().getName());
    return userService.getUserById(userId);
  }

  private Set<User> getRoomUsers() {
    Set<SimpUser> roomUsers = simpUserRegistry.getUsers();
    Set<User> users = new HashSet<User>();
    for (SimpUser user : roomUsers) {
      users.add(userService.getUserById(Long.parseLong(user.getName())));
    }
    return users;
  }

  String encodeUsersList(User userOne, User userTwo) {
    ObjectMapper objectMapper = new ObjectMapper();
    Set<User> users = new HashSet<User>();
    users.add(userOne);
    users.add(userTwo);
    try {
      String json = objectMapper.writeValueAsString(users);
      return json;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @MessageMapping("/invitations")
  public void sendInvite(SimpMessageHeaderAccessor sha, @Payload InboundInvitationMessage message) {
    System.out.println("sendInvite called");
    OutboundInvitationMessage outboundMessage = new OutboundInvitationMessage(message.getType());
    User inviter = getSender(sha);
    outboundMessage.setSender(inviter);
    User invitee;
    if (message.getType() == InvitationMessageType.INVITE) {
      if(message.getInviteeId() == null) {
       throw new IllegalArgumentException("Invitee ID cannot be null");
      }
      invitee = userService.getUserById(message.getInviteeId());
      Invitation newInvitation = new Invitation(inviter, invitee);
      invitationService.saveInvitation(newInvitation);
      
    } else {
      invitee = invitationService.getInvitationById(message.getInvitationId()).getInvitee();
      invitationService.deleteInvitation(message.getInvitationId());
      ///
      if (message.getType() == InvitationMessageType.DECLINE) {
        //
      } else if (message.getType() == InvitationMessageType.ACCEPT) {


        Game game = new Game(inviter, invitee, User.createAIUser());
        gamesService.saveGame(game);

        // Let everyone know these players are leaving to play a game
        OutboundMessage chatMessage = new OutboundMessage(PublicMessageType.STARTING_GAME,
            encodeUsersList(inviter, invitee), inviter);
        simpMessagingTemplate.convertAndSend("topic/public", chatMessage);

      }
    }

    List<Invitation> inviteeInvitations = invitationService.getUserInvitations(invitee.getId());
    List<Invitation> inviterInvitations = invitationService.getUserInvitations(inviter.getId());

    outboundMessage.setUserInvitations(inviteeInvitations);
    simpMessagingTemplate.convertAndSendToUser(String.valueOf(invitee.getId()), "/queue/messages", outboundMessage);
    outboundMessage.setUserInvitations(inviterInvitations);
    simpMessagingTemplate.convertAndSendToUser(String.valueOf(inviter.getId()), "/queue/messages", outboundMessage);

  }

  // @MessageMapping("/reply-to-invite")
  // @SendTo("/topic/public")
  // public OutboundMessage replyToInvite(SimpMessageHeaderAccessor sha, @Payload
  // InviteReply reply) {
  // // either get inviter from database or from the message
  // User invitee = getSender(sha);
  // // Create game in database
  // simpMessagingTemplate.convertAndSendToUser(String.valueOf(inviteeId),
  // "/queue/messages", reply);
  // return new OutboundMessage(OutboundMessage.PublicMessageType.STARTING_GAME,
  // "reply
  // to invite", invitee);
  // }

  @MessageMapping("/chat.sendMessage")
  @SendTo("/topic/public")
  public OutboundMessage sendMessage(@Payload InboundMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
    OutboundMessage outboundMessage = new OutboundMessage(chatMessage.getType(), chatMessage.getContent(),
        getSender(headerAccessor));
    return outboundMessage;
  }

  @MessageMapping("/chat.joinRoom")
  @SendTo("/topic/public")
  public void joinRoom(SimpMessageHeaderAccessor headerAccessor) {
    System.out.println("joinRoom called");
    // Send the list of users in the chat room
    Set<User> users = getRoomUsers();
    simpMessagingTemplate.convertAndSend("/topic/users-in-chat-room", users);
    // Send a message to the chat room that a user has joined
    OutboundMessage chatMessage = new OutboundMessage();
    User sender = getSender(headerAccessor);
    if (sender != null) {
      // headerAccessor.getSessionAttributes().put("username", sender.getName());
      chatMessage.setSender(sender);
      chatMessage.setContent(sender.getName() + " has joined the chat room!");
      chatMessage.setType(OutboundMessage.PublicMessageType.JOIN);
      System.out.println("type set to " + chatMessage.getType());
    }

    simpMessagingTemplate.convertAndSend("/topic/public", chatMessage);
  }

  @MessageMapping("/game/{room}")
  @SendTo("/topic/{room}")
  public OutboundMessage sendMessageToRoom(@DestinationVariable String room, @Payload OutboundMessage chatMessage) {
    // Kick out the user if they are not in the game
    // Also send them a private message saying they were kicked out

    // AFter that we know all users receiving GameSnapshots are supposed to get them
    // We can simply send the game snapshot to all users in the room
    // System.out.println("new chat msg: " + chatMessage.getContent() + " sent to
    // room " + room);
    return chatMessage;
  }

}