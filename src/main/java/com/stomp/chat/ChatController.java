package com.stomp.chat;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

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
  public ChatMessage addUser(@Payload ChatMessage chatMessage,
      SimpMessageHeaderAccessor headerAccessor) {
    System.out.println("adding user: " + chatMessage.getSender());
    // Add username in web socket session
    headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
    User user = (User) headerAccessor.getUser();
    // System.out.println(headerAccessor.getFirstNativeHeader("token"));
    // System.out.println("getting cookie from Principal:");
    // System.out.println(headerAccessor.getUser().getName());
    System.out.println(user.getId());
    return chatMessage;
  }

}