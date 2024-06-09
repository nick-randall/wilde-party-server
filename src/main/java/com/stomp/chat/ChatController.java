package com.stomp.chat;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

  final SessionRepo sessionRepo = new SessionRepo(Database.getInstance());
  final UserRepo userRepo = new UserRepo(Database.getInstance());

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
    // System.out.println("in chat.adduser: " + chatMessage.getSender());
    // Add username in web socket session
    // headerAccessor.getSessionAttributes().put("user",
    // createUserRequest.getSender());
    // headerAccessor.getSessionAttributes().put("userId",
    // chatMessage.getSender());
    Object currUser = headerAccessor.getSessionAttributes().get("user");
    System.out.println(currUser);
    OutboundMessage chatMessage = new OutboundMessage();
    Principal principal = headerAccessor.getUser();
    if (principal != null) {
      User user = (User) principal;
      headerAccessor.getSessionAttributes().put("username", user.getName());
      chatMessage.setSender(user);
    }
    return chatMessage;
  }

}