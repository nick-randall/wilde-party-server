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
  public ChatMessage sendMessage(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
    System.out.println("new chat msg: " + chatMessage.getContent());
    System.out.println("getting cookie from Principal:");

    System.out.println(headerAccessor.getUser().getName());
    return chatMessage;
  }

  @MessageMapping("/chat.sendMessage/{room}")
  @SendTo("/topic/{room}")
  public ChatMessage sendMessageToRoom(@DestinationVariable String room, @Payload ChatMessage chatMessage) {
    System.out.println("new chat msg: " + chatMessage.getContent() + " sent to room " + room);
    return chatMessage;
  }

  @MessageMapping("/chat.addUser")
  @SendTo("/topic/public")
  public ChatMessage addUser(@Payload InboundMessage createUserRequest,
      SimpMessageHeaderAccessor headerAccessor) {
    // System.out.println("in chat.adduser: " + chatMessage.getSender());
    // Add username in web socket session
    // headerAccessor.getSessionAttributes().put("user",
    // createUserRequest.getSender());
    // headerAccessor.getSessionAttributes().put("userId",
    // chatMessage.getSender());
    ChatMessage chatMessage = new ChatMessage();
    Principal user = headerAccessor.getUser();
    if (user instanceof User) {
      // if (user instanceof UnnamedUser) {
      // UnnamedUser unnamedUser = (UnnamedUser) user;
      // User newUser = new User(chatMessage.getSender());
      // userRepo.addUser(newUser);
      // sessionRepo.addSession(newUser.id, unnamedUser.token);
      // } else {
      System.out.println(user.getName());
      headerAccessor.getSessionAttributes().put("username", user.getName());
      chatMessage.setSender((User) user);
    }
    // }

    // System.out.println(headerAccessor.getFirstNativeHeader("token"));
    // System.out.println("getting cookie from Principal:");
    // System.out.println(headerAccessor.getUser().getName());
    // System.out.println(user.getId());
    return chatMessage;
  }

}