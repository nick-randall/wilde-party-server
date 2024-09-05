package com.wildeparty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import java.util.regex.Pattern;
import java.util.Set;
import java.util.regex.Matcher;

import com.wildeparty.backend.GamesService;
import com.wildeparty.backend.UserService;
import com.wildeparty.model.OutboundMessage;
import com.wildeparty.model.User;
import com.wildeparty.model.DTO.PrivateMessageDTO;
import com.wildeparty.model.DTO.PrivateMessageType;

@Component
public class WebSocketEventListener {

  private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

  @Autowired
  private SimpMessageSendingOperations messagingTemplate;
  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;
  @Autowired
  private UserService userService;
  @Autowired
  private GamesService gamesService;

  @EventListener
  public void handleWebSocketConnectListener(SessionConnectedEvent event) {
    logger.info("Received a new web socket connection");
  }

  @EventListener
  public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
    logger.info("Received a new web socket subscription");
    // Get the user and destination of subscription
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
    Long userId = Long.parseLong(accessor.getUser().getName());
    User user = userService.getUserById(userId);
    String destination = (String) accessor.getHeader(SimpMessageHeaderAccessor.DESTINATION_HEADER);
    System.out.println("User " + user.getName() + " wants to subscribe to destination " + destination);

    // Inform others when joining the chat room
    if (destination.equals("/topic/public")) {
      OutboundMessage chatMessage = new OutboundMessage();
      chatMessage.setType(OutboundMessage.PublicMessageType.JOIN);
      messagingTemplate.convertAndSend("/chat.joinRoom", chatMessage);
      return;
    }

    // If this is a subscription to a game, check if user is in game
    Pattern pattern = Pattern.compile("(\\/topic\\/game\\/)(.+)");
    Matcher matcher = pattern.matcher(destination);
    matcher.find();
    if (matcher.matches()) {
      String gameId = matcher.group(1);
      System.out.println("User " + user.getName() + " wants to subscribe to game " + gameId);
      boolean userIsInGame = gamesService.isUserInGame(user.getId(), Long.getLong(gameId));
      if (!userIsInGame) {
        System.out.println("User " + user.getName() + " is not in game " + gameId + " and cannot subscribe");
        PrivateMessageDTO message = new PrivateMessageDTO(PrivateMessageType.ERROR_SUBSCRIBING, null,
            accessor.getSubscriptionId(), "You are not in this game");
        simpMessagingTemplate.convertAndSendToUser(event.getUser().getName(), "/queue/messages", message);
      }

    }
  }

  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

    User user = (User) headerAccessor.getSessionAttributes().get("user");
    if (user != null) {
      logger.info("User Disconnected : " + user);

      OutboundMessage chatMessage = new OutboundMessage();
      chatMessage.setType(OutboundMessage.PublicMessageType.LEAVE);
      chatMessage.setSender(user);

      messagingTemplate.convertAndSend("/topic/public", chatMessage);
    }
  }
}