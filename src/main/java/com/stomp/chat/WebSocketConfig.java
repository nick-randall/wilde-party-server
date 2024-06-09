package com.stomp.chat;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.WebUtils;

import com.stomp.chat.OutboundMessage.MessageType;

import ch.qos.logback.core.joran.event.EndEvent;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  final SessionRepo sessionRepo = new SessionRepo(Database.getInstance());
  final UserRepo userRepo = new UserRepo(Database.getInstance());

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws").setAllowedOriginPatterns("*").addInterceptors(httpSessionHandshakeInterceptor())
        .withSockJS();
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.setApplicationDestinationPrefixes("/app");
    registry.enableSimpleBroker("/topic");
  }

  @Bean
  public HandshakeInterceptor httpSessionHandshakeInterceptor() {
    return new HandshakeInterceptor() {
      @Override
      public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
          Map<String, Object> attributes) throws Exception {
        System.out.println("before handshake");
        if (request instanceof ServletServerHttpRequest) {
          ServletServerHttpRequest servletServerRequest = (ServletServerHttpRequest) request;
          HttpServletRequest servletRequest = servletServerRequest.getServletRequest();
          Cookie[] existingCookies = servletRequest.getCookies();
          if (existingCookies != null) {
            if (existingCookies.length > 0) {
              Cookie cookie = existingCookies[0];
              attributes.put("token", cookie.getValue());
              return true;
            }
          }
        }
        return false;
      }

      @Override
      public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
          Exception exception) {
      }
    };
  }

  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    ChannelInterceptor interceptor = new ChannelInterceptor() {
      @Override
      public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
          Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
          try {
            String token = (String) sessionAttributes.get("token");
            if (token != null) {
              Session existingSession = sessionRepo.getSessionFromSessionToken(token);
              if (existingSession != null) {
                System.out.println("adding user to accessor");
                User user = userRepo.getUser(existingSession.userId);
                accessor.setUser(user);
              }
            }
          } catch (Exception e) {
            System.err.println(e);
          }
        } else if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
          System.err.println("SUBSCRIBE msg");
          Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
          String token = (String) sessionAttributes.get("token");
          if (token != null) {
            Session existingSession = sessionRepo.getSessionFromSessionToken(token);
            if (existingSession != null && existingSession.isInChatRoom) {
              System.out.println("blocking adding to chat room");
              Object o = message.getPayload();
              String x = "9";
              // InboundMessage msgPayload = (InboundMessage) message.getPayload();
              // msgPayload.setType(MessageType.IGNORE);
              // Message<Object> m = new Message<Object>() {

              //   @Override
              //   public Object getPayload() {
              //     return msgPayload;
              //   }

              //   @Override
              //   public MessageHeaders getHeaders() {
              //     return message.getHeaders();
              //   }

              // };
              // return m;
            }
          }
        }
        return message;
      }
    };
    registration.interceptors(interceptor);

  }
}
