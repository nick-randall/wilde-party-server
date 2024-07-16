package com.stomp.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stomp.chat.model.PushMessage;

import jakarta.servlet.http.HttpServletRequest;
@RestController
public class WebsocketAPIController {

  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  // For push notifications
  @PostMapping("/notify")
  public void notifyUsers(@RequestBody PushMessage message) {
    String destination = "/topic/public";
    simpMessagingTemplate.convertAndSend(destination, message);
  }
}
