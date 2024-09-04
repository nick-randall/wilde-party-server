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

import com.wildeparty.model.OutboundMessage;

@Component
public class WebSocketEventListener {

  private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

  @Autowired
  private SimpMessageSendingOperations messagingTemplate;
  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  @EventListener
  public void handleWebSocketConnectListener(SessionConnectedEvent event) {
    logger.info("Received a new web socket connection");
  }

  @EventListener
  public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
    logger.info("Received a new web socket subscription");
    MessageHeaders headers = event.getMessage().getHeaders();
    for (String key : headers.keySet()) {
      System.out.println(key + " : " + headers.get(key));
    }
    System.out.println(event.getSource());
    System.out.println(event.getUser());
    simpMessagingTemplate.convertAndSendToUser(event.getUser().getName(), "/queue/messages",
        "You have subscribed to the chat");
    // MAYBE a way to remove the subscription from the user?
    // Set<SimpSession> sessions = simpUserRegistry.getUser(event.getUser().getName()).getSessions();
    
    // SimpSubscription subscriptionToRemove = null;
    // for (SimpSession sesh : sessions) {
    //   for (SimpSubscription sub : sesh.getSubscriptions()) {
    //     // if (sub.getDestination().equals(dest)) {
    //     System.out.println("subscription destination has an id as: " + sub.getDestination());
    //     System.out.println("subscription id is: " + sub.getId());

    //     subscriptionToRemove = sub;
    //     // }
    //   }
    // }
    // if (subscriptionToRemove != null) {
    //   System.out.println("Removing session: " + subscriptionToRemove.getId());
    //   for(SimpSession sub : sessions) {
    //     System.out.println("sub id: " + sub.getId());
    //     boolean removedSuccessfully = sub.getSubscriptions().remove(subscriptionToRemove);
    //     System.out.println("removed successfully? " + removedSuccessfully);

    //   }
    // }

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