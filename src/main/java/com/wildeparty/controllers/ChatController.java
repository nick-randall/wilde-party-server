package com.wildeparty.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.messaging.simp.user.SimpSubscription;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;

import com.wildeparty.Database;
import com.wildeparty.SessionRepo;
import com.wildeparty.User;
import com.wildeparty.backend.SessionService;
import com.wildeparty.backend.UserService;
import com.wildeparty.model.OutboundMessage;
import com.wildeparty.model.InboundMessage;

@Controller
public class ChatController {

  @Autowired
  SessionService sessionService;
  @Autowired
  UserService userService;
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

  @MessageMapping("/invite")
  public void sendInvite(SimpMessageHeaderAccessor sha, @Payload int inviteeId) {
    System.out.println(sha.getSessionAttributes().get("token"));
    User inviter = getSender(sha);
    String message = "You were invited to a game by " + inviter.getName();

    simpMessagingTemplate.convertAndSendToUser(String.valueOf(inviteeId), "/queue/messages", message);
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
  // return new OutboundMessage(OutboundMessage.MessageType.STARTING_GAME, "reply
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
    // Send the list of users in the chat room
    Set<User> users = getRoomUsers();
    simpMessagingTemplate.convertAndSend("/topic/users-in-chat-room", users);
    // Send a message to the chat room that a user has joined
    OutboundMessage chatMessage = new OutboundMessage();
    User sender = getSender(headerAccessor);
    if (sender != null) {
      headerAccessor.getSessionAttributes().put("username", sender.getName());
      chatMessage.setSender(sender);
      chatMessage.setContent(sender.getName() + " has joined the chat room!");
      chatMessage.setType(OutboundMessage.MessageType.JOIN);
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