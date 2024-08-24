package com.wildeparty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wildeparty.model.PushMessage;

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
