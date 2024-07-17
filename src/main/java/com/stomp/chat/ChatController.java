package com.stomp.chat;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

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

@Controller
public class ChatController {

  final SessionRepo sessionRepo = new SessionRepo(Database.getInstance());
  final UserRepo userRepo = new UserRepo(Database.getInstance());

  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;
  @Autowired
  private  SimpUserRegistry simpUserRegistry;

  private User getSender(SimpMessageHeaderAccessor sha) {
    // In WebsocketConfig, we set the userId as the Principal name
    // Normally, the Principal getName() method returns the username
    // which, if it were an email address, would be unique.
    // Since we are using an integer userId, we have set it as the Principal
    // getName()
    // value.
    int userId = Integer.parseInt(sha.getUser().getName());
    return userRepo.getUserById(userId);
  }

  private Set<User> getRoomUsers() {
   Set<SimpUser> roomUsers = simpUserRegistry.getUsers();
   Set<User> users = new HashSet<User>(); 
   for (SimpUser user : roomUsers) {
     users.add(userRepo.getUserById(Integer.parseInt(user.getName())));
   }
   return users;
  }

  @MessageMapping("/invite")
  public void send(SimpMessageHeaderAccessor sha, @Payload int inviteeId) {
    System.out.println(sha.getSessionAttributes().get("token"));
    User inviter = getSender(sha);
    String message = "Hello from " + inviter.getName();

    simpMessagingTemplate.convertAndSendToUser(String.valueOf(inviteeId), "/queue/messages", message);
  }

  @MessageMapping("/chat.sendMessage")
  @SendTo("/topic/public")
  public OutboundMessage sendMessage(@Payload InboundMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
    OutboundMessage outboundMessage = new OutboundMessage(chatMessage.getType(), chatMessage.getContent(),
        (User) headerAccessor.getUser());
    return outboundMessage;
  }

  @MessageMapping("/chat.sendMessage/{room}")
  @SendTo("/topic/{room}")
  public OutboundMessage sendMessageToRoom(@DestinationVariable String room, @Payload OutboundMessage chatMessage) {
    System.out.println("new chat msg: " + chatMessage.getContent() + " sent to room " + room);
    return chatMessage;
  }

  @MessageMapping("/chat.addUser")
  @SendTo("/topic/public")
  public OutboundMessage addUser(@Payload InboundMessage createUserRequest,
      SimpMessageHeaderAccessor headerAccessor) {
    System.out.println("in chat.adduser");
    // Add username in web socket session
    // headerAccessor.getSessionAttributes().put("user",
    // createUserRequest.getSender());
    // headerAccessor.getSessionAttributes().put("userId",
    // chatMessage.getSender());
    // Object currUser = headerAccessor.getSessionAttributes().get("user");
    // System.out.println(currUser);
    OutboundMessage chatMessage = new OutboundMessage();
    User sender = getSender(headerAccessor);
    // Principal principal = headerAccessor.getUser();
    if (sender != null) {
      // User user = (User) principal;
      headerAccessor.getSessionAttributes().put("username", sender.getName());
      chatMessage.setSender(sender);
    }
    return chatMessage;
  }

}