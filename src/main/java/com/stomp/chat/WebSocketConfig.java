package com.stomp.chat;

import java.security.Principal;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  final SessionRepo sessionRepo = new SessionRepo(Database.getInstance());
  final UserRepo userRepo = new UserRepo(Database.getInstance());

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws").setAllowedOriginPatterns("*")// .addInterceptors(httpSessionHandshakeInterceptor())
        .withSockJS();
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.setApplicationDestinationPrefixes("/app");
    registry.enableSimpleBroker("/topic");
  }

  // @Bean
  // public HandshakeInterceptor httpSessionHandshakeInterceptor() {
  // return new HandshakeInterceptor() {
  // @Override
  // public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse
  // response,
  // WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception
  // {
  // System.out.println("before handshake");
  // if (request instanceof ServletServerHttpRequest) {
  // ServletServerHttpRequest servletServerRequest = (ServletServerHttpRequest)
  // request;
  // HttpServletRequest servletRequest = servletServerRequest.getServletRequest();
  // Cookie[] existingCookies = servletRequest.getCookies();
  // Enumeration<String> x = servletRequest.getHeaderNames();
  // while(x.asIterator().hasNext()) {
  // System.out.println(x.nextElement());

  // }

  // System.out.println(existingCookies);
  // System.out.println(servletRequest.getHeader("token"));
  // if (existingCookies != null) {
  // // System.out.println("No cookie");
  // // UUID uuid = UUID.randomUUID();
  // // Cookie cookie = new Cookie("SESSION", uuid.toString());
  // // response.addCookie(cookie);
  // System.out.println(existingCookies);

  // }
  // Cookie cookie = WebUtils.getCookie(servletRequest, "SESSION");
  // if (cookie != null) {
  // System.out.println(cookie.getValue());
  // attributes.put("cookie", cookie.getValue());
  // }
  // }
  // return true;
  // }

  // @Override
  // public void afterHandshake(ServerHttpRequest request, ServerHttpResponse
  // response, WebSocketHandler wsHandler,
  // Exception exception) {
  // System.out.println("after handshake");
  // }
  // };
  // }

  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    ChannelInterceptor interceptor = new ChannelInterceptor() {
      @Override
      public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
          // Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
          // List<String> authorization = accessor.getNativeHeader("Authorization");
          List<String> tokens = accessor.getNativeHeader("token");
          String token = tokens.get(0);
          if (tokens.size() > 0) {
            token = token.replaceAll("SESSION=", "");
            System.out.println(token);
            int userId = sessionRepo.getUserIdFromSessionToken(token);
            System.out.println(userId);
            if (userId == -1) {
              UnnamedUser user = new UnnamedUser(token);
              // User user = new User("l");
              accessor.setUser(user);
            } else {
              System.out.println("adding user to accessor");
              User user = userRepo.getUser(userId);
              accessor.setUser(user);
            }
          }
        }

        return message;
      }
    };
    registration.interceptors(interceptor);

  }
}
