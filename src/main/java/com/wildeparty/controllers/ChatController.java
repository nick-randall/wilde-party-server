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
import com.wildeparty.model.DTO.GameDTO;
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

  OutboundInvitationMessage addInvitationsListToMessage(OutboundInvitationMessage message, User user){
    List<Invitation> sentInvitations = invitationService.getSentInvitationsByUserId(user.getId());
    List<Invitation> receivedInvitations = invitationService.getReceivedInvitationsByUserId(user.getId());
    message.setSentInvitations(sentInvitations);
    message.setReceivedInvitations(receivedInvitations);
    return message;
  }

  @MessageMapping("/invitations")
  public void sendInvite(SimpMessageHeaderAccessor sha, @Payload InboundInvitationMessage message) {
    System.out.println("sendInvite called");
   
    if (message.getType() == InvitationMessageType.INVITE) {
      User inviter = getSender(sha);
      User invitee = userService.getUserById(message.getInviteeId());
      // Create a new invitation and save it to the database
      Invitation newInvitation = new Invitation(inviter, invitee);
      invitationService.saveInvitation(newInvitation);
      // Send the invitation to the invitee
      OutboundInvitationMessage inviterMessage = new OutboundInvitationMessage(message.getType());
      inviterMessage.setMessage("You invited " + invitee.getName() + " to play a game!");
      addInvitationsListToMessage(inviterMessage, inviter);
      
      OutboundInvitationMessage inviteeMessage = new OutboundInvitationMessage(message.getType());
      inviteeMessage.setMessage(inviter.getName() + " invited you to play a game!");
      addInvitationsListToMessage(inviteeMessage, invitee);
      simpMessagingTemplate.convertAndSendToUser(String.valueOf(inviter.getId()), "/queue/messages", inviterMessage);
      simpMessagingTemplate.convertAndSendToUser(String.valueOf(invitee.getId()), "/queue/messages", inviteeMessage);
    } 
    else {
      User responder = getSender(sha);
      User originalInviter = invitationService.getInvitationById(message.getInvitationId()).getInviter();
      invitationService.deleteInvitation(message.getInvitationId());
      ///
      if (message.getType() == InvitationMessageType.DECLINE) {
        OutboundInvitationMessage responderMessage = new OutboundInvitationMessage(message.getType());
        responderMessage.setMessage("You declined " + originalInviter.getName() + "'s invitation to play a game!");
        addInvitationsListToMessage(responderMessage, originalInviter);
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(responder.getId()), "/queue/messages", responderMessage);
        
        //
        OutboundInvitationMessage originalInviterMessage = new OutboundInvitationMessage(message.getType());
        originalInviterMessage.setMessage(responder.getName() + " declined your invitation to play a game!");
        addInvitationsListToMessage(originalInviterMessage, originalInviter);
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(originalInviter.getId()), "/queue/messages", originalInviterMessage);

      } else if (message.getType() == InvitationMessageType.ACCEPT) {
        User aiUser = User.createAIUser();
        userService.saveUser(aiUser);
        Game game = new Game(originalInviter, responder, aiUser);
        gamesService.saveGame(game);

        OutboundInvitationMessage responderMessage = new OutboundInvitationMessage(message.getType());
        responderMessage.setMessage("You accepted " + originalInviter.getName() + "'s invitation to play a game!");
        responderMessage.setGame(GameDTO.fromGame(game));
        addInvitationsListToMessage(responderMessage, responder);
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(responder.getId()), "/queue/messages", responderMessage);

        OutboundInvitationMessage originalInviterMessage = new OutboundInvitationMessage(message.getType());
        originalInviterMessage.setMessage(responder.getName() + " accepted your invitation to play a game!");
        originalInviterMessage.setGame(GameDTO.fromGame(game));
        addInvitationsListToMessage(originalInviterMessage, originalInviter);
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(originalInviter.getId()), "/queue/messages", originalInviterMessage);

        // Let everyone know these players are leaving to play a game
        OutboundMessage chatMessage = new OutboundMessage(PublicMessageType.STARTING_GAME,
            encodeUsersList(originalInviter, responder), responder);
        simpMessagingTemplate.convertAndSend("topic/public", chatMessage);

        invitationService.deleteAllUserInvitations(originalInviter.getId());
        invitationService.deleteAllUserInvitations(responder.getId());

      }
    }

  }

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