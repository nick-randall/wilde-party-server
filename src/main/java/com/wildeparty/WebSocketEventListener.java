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
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import java.util.regex.Pattern;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wildeparty.backend.GamesService;
import com.wildeparty.backend.InvitationService;
import com.wildeparty.backend.UserService;
import com.wildeparty.model.Invitation;
import com.wildeparty.model.OutboundMessage;
import com.wildeparty.model.User;
import com.wildeparty.model.DTO.GameDTO;
import com.wildeparty.model.DTO.GameSnapshotDTO;
import com.wildeparty.model.DTO.OutboundChatRoomMessage;
import com.wildeparty.model.DTO.OutboundChatRoomMessageType;
import com.wildeparty.model.DTO.OutboundPersonalGameMessage;
import com.wildeparty.model.DTO.PrivateMessageDTO;
import com.wildeparty.model.DTO.PrivateMessageType;
import com.wildeparty.model.gameElements.Game;
import com.wildeparty.model.DTO.OutgoingGameMessage;
import com.wildeparty.model.DTO.OutgoingGameMessageType;

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
  @Autowired
  private SimpUserRegistry simpUserRegistry;
  @Autowired
  private InvitationService invitationService;

  private String getRoomUsers() {
    Set<SimpUser> roomUsers = simpUserRegistry.getUsers();
    Set<User> users = new HashSet<User>();
    for (SimpUser user : roomUsers) {
      users.add(userService.getUserById(Long.parseLong(user.getName())));
    }
    // Turn the set of users into JSON
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      String json = objectMapper.writeValueAsString(users);
      return json;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  // private Set<User> getRoomUsers() {
  // Set<SimpUser> roomUsers = simpUserRegistry.getUsers();
  // Set<User> users = new HashSet<User>();
  // for (SimpUser user : roomUsers) {
  // users.add(userService.getUserById(Long.parseLong(user.getName())));
  // }
  // return users;
  // }

  @EventListener
  public void handleWebSocketConnectListener(SessionConnectedEvent event) {
    logger.info("Received a new web socket connection");
  }

  @EventListener
  public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
    logger.info("Received a new web socket subscription");
    // Get the user and destination of subscription

    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
    if (accessor == null) {
      System.out.println("accessor is null!!! No user to subscribe!");
      return;
    }
    Long userId = Long.parseLong(accessor.getUser().getName());
    User user = userService.getUserById(userId);
    String destination = (String) accessor.getHeader(SimpMessageHeaderAccessor.DESTINATION_HEADER);
    System.out.println("User " + user.getName() + " wants to subscribe to destination " + destination);

    // Inform others when joining the chat room
    if (destination.equals("/topic/public")) {
      String users = getRoomUsers();
      OutboundMessage chatMessage = new OutboundMessage();
      chatMessage.setSender(user);
      chatMessage.setType(OutboundMessage.PublicMessageType.JOIN);
      chatMessage.setContent(users);
      System.out.println("Sending join message to /chat.joinRoom");
      messagingTemplate.convertAndSend("/topic/public", chatMessage);
      // TODO send invitations plus gameData to user who just joined
      OutboundChatRoomMessage dataMessage = new OutboundChatRoomMessage(OutboundChatRoomMessageType.UPDATE);
      List<Game> games = gamesService.getUserActiveGames(user);
      if (games.size() > 0) {
        dataMessage.setGame(GameDTO.fromGame(games.get(0)));
      }
      List<Invitation> sentInvitations = invitationService.getSentInvitationsByUserId(userId);
      List<Invitation> receivedInvitations = invitationService.getReceivedInvitationsByUserId(userId);
      dataMessage.setSentInvitations(sentInvitations);
      dataMessage.setReceivedInvitations(receivedInvitations);
      messagingTemplate.convertAndSendToUser(user.getId().toString(), "/queue/messages", dataMessage);
      return;
    }

    // If this is a subscription to a game, check if user is in game
    Pattern pattern = Pattern.compile("(\\/topic\\/game\\/)(.+)");
    Matcher matcher = pattern.matcher(destination);
    matcher.find();
    if (matcher.matches()) {
      String gameId = matcher.group(2);
      System.out.println("User " + user.getName() + " wants to subscribe to game " + gameId);
      boolean userIsInGame = gamesService.isUserInGame(user, Long.parseLong(gameId));
      // User userAsUser = (User) accessor.getUser();
      // String userId = userAsUser.getId().toString();
      if (!userIsInGame) {
        System.out.println("User " + user.getName() + " is not in game " + gameId + " and cannot subscribe");
        OutboundPersonalGameMessage message = new OutboundPersonalGameMessage(
            OutboundPersonalGameMessage.MessageType.NOT_IN_GAME_ERROR, null);
        simpMessagingTemplate.convertAndSendToUser(userId.toString(), "/queue/messages", message);
      } else {
        System.out.println("User " + user.getName() + " is in game " + gameId + " and can subscribe");
        // If so send the user the game data
        Game game = gamesService.getGame(Long.parseLong(gameId));
        // OutboundPersonalGameMessage message = new OutboundPersonalGameMessage(
        //     OutboundPersonalGameMessage.MessageType.INITIAL_GAME_SNAPSHOTS, game.getGameSnapshots());
        

        // simpMessagingTemplate.convertAndSendToUser(userId.toString(), "/queue/messages", message);
        OutgoingGameMessage message = new OutgoingGameMessage(game.getGameSnapshots());
        // Let's just use the normal broadcast to send snapshots to all users in the game -- client code will filter out the right ones
        simpMessagingTemplate.convertAndSend("/topic/game/" + gameId, message);

        // If so let everyone know they have joined
        // OutgoingGameMessage message = new OutgoingGameMessage();
        // message.setType(OutgoingGameMessageType.JOIN);
        // message.setMessage(user.getName() + " joined the game");
        // message.setActivePlayers(null);

        // simpMessagingTemplate.convertAndSend(destination, message);
      }

    }
  }

  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

    User user = (User) headerAccessor.getSessionAttributes().get("user");
    if (user != null) {
      logger.info("User Disconnected : " + user);
      String users = getRoomUsers();
      OutboundMessage chatMessage = new OutboundMessage();
      chatMessage.setType(OutboundMessage.PublicMessageType.LEAVE);
      chatMessage.setSender(user);
      chatMessage.setContent(users);

      messagingTemplate.convertAndSend("/topic/public", chatMessage);
    }
  }
}