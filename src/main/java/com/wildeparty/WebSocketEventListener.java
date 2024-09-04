package com.wildeparty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import java.util.regex.Pattern;
import java.util.regex.Matcher;



import com.wildeparty.backend.GamesService;
import com.wildeparty.model.OutboundMessage;

@Component
public class WebSocketEventListener {

  private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

  @Autowired
  private SimpMessageSendingOperations messagingTemplate;
  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;
  @Autowired
  private GamesService gamesService;

  @EventListener
  public void handleWebSocketConnectListener(SessionConnectedEvent event) {
    logger.info("Received a new web socket connection");
  }

  @EventListener
  public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
    logger.info("Received a new web socket subscription");
    // User user = (User) event.getUser();
    // Pattern pattern = Pattern.compile("[a-z_]+");
    // Matcher matcher = pattern.matcher(event.getMessage().getPayload());
    // matcher.find();
    // gamesService.isUserInGame(user.getId(), null);
    simpMessagingTemplate.convertAndSendToUser(event.getUser().getName(), "/queue/messages",
        "You have subscribed to the chat");
  }

  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

    User user = (User) headerAccessor.getSessionAttributes().get("user");
    if (user != null) {
      logger.info("User Disconnected : " + user);

      OutboundMessage chatMessage = new OutboundMessage();
      chatMessage.setType(OutboundMessage.MessageType.LEAVE);
      chatMessage.setSender(user);

      messagingTemplate.convertAndSend("/topic/public", chatMessage);
    }
  }
}