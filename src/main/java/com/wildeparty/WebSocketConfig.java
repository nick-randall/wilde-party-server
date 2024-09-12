package com.wildeparty;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.messaging.simp.user.SimpSubscription;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.wildeparty.backend.GamesService;
import com.wildeparty.backend.SessionService;
import com.wildeparty.backend.UserService;
import com.wildeparty.model.Game;
import com.wildeparty.model.Session;
import com.wildeparty.model.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Autowired
  SessionService sessionService;
  @Autowired
  UserService userService;
  @Autowired
  GamesService gamesService;

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws").setAllowedOriginPatterns("*")
        .addInterceptors(httpSessionHandshakeInterceptor())
        .withSockJS();
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableSimpleBroker("/queue", "/topic");
    registry.setApplicationDestinationPrefixes("/app");
    registry.setUserDestinationPrefix("/users");
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
        System.out.println("SOMETHING WENT WRONG");
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
          String token = (String) sessionAttributes.get("token");
          if (token != null) {
            User existingUser = userService.getUserBySessionToken(token);
            if (existingUser != null) {
              // User user = userService.getUserById(existingSession.getUserId());
              // Because we are not using Spring Security, we need to
              // set the user in the accessor in a hacky way, that allows
              // us to access the userId in the controller.
              IdContainer idContainer = new IdContainer(existingUser.getId());
              System.out.println("adding principal '" + idContainer.getName() + "' to accessor");
              accessor.setUser(idContainer);
            } else {
              System.out.println("existingUser is null");
              // return new SubscriptionRejectionMessage(accessor.getSubscriptionId());
              return null;

            }

          }

        }
        return message;
      }
    };
    registration.interceptors(interceptor);

  }

}

class IdContainer implements Principal {

  private Long id;

  public IdContainer(Long id) {
    this.id = id;
  }

  @Override
  public String getName() {
    return String.valueOf(id);
  }

}

class SubscriptionRejectionMessage implements Message {

  SubscriptionRejectionMessage(String subId) {
    this.subId = subId;
  }

  private String subId;

  @Override
  public Object getPayload() {
    return null;
  }

  @Override
  public MessageHeaders getHeaders() {
    Map<String, Object> headers = new HashMap<>();
    headers.put(SimpMessageHeaderAccessor.DESTINATION_HEADER, "/subscription-rejected");
    headers.put(SimpMessageHeaderAccessor.MESSAGE_TYPE_HEADER, StompCommand.MESSAGE);
    headers.put(SimpMessageHeaderAccessor.SESSION_ID_HEADER, subId);
    return new MessageHeaders(headers);
  }

}
