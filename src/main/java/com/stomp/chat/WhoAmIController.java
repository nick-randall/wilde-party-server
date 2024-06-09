package com.stomp.chat;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
public class WhoAmIController {

  final SessionRepo sessionRepo = new SessionRepo(Database.getInstance());
  final UserRepo userRepo = new UserRepo(Database.getInstance());

  private Cookie createCookie() {

    System.out.println("No cookie");
    UUID uuid = UUID.randomUUID();
    Cookie cookie = new Cookie("SESSION", uuid.toString());
    return cookie;

  }

  private Cookie extractCookie(HttpServletRequest request) {
    Cookie[] existingCookies = request.getCookies();
    if (existingCookies != null) {
      if (existingCookies.length > 0) {
        return existingCookies[0];
      }
    }
    return null;
  }

  @GetMapping("/session")
  public ResponseEntity<String> getSession(HttpServletResponse response, HttpServletRequest request) {
    System.out.println("eating cookies");

    SessionResponse responseBody = null;

    Cookie existingCookie = extractCookie(request);
    if (existingCookie != null) {
      String cookieValue = existingCookie.getValue();
      cookieValue = cookieValue.replaceAll("SESSION=", "");
      Session existingSession = sessionRepo.getSessionFromSessionToken(cookieValue);
      if (existingSession != null) {
        responseBody = SessionResponse.foundExistingSession;
      } else {
        // Create expired cookie from existing cookie and send it to remove old cookie
        existingCookie.setMaxAge(0);
        responseBody = SessionResponse.removedExpiredSession;
        response.addCookie(existingCookie);
      }
    } else {
      Cookie newCookie = createCookie();
      responseBody = SessionResponse.createdNewSession;
      response.addCookie(newCookie);

    }
    return ResponseEntity.ok().body(responseBody.getName());

  }

  @PostMapping("/whoami")
  public ResponseEntity<User> whoAmI(HttpServletResponse response, HttpServletRequest request) {
    User user = null;
    Cookie existingCookie = extractCookie(request);

    if (existingCookie != null) {
      Session existingSession = sessionRepo.getSessionFromSessionToken(existingCookie.getValue());
      if (existingSession != null) {
        user = userRepo.getUser(existingSession.userId);
      }
    }
    return ResponseEntity.ok().body(user);
  }

  @PostMapping("/addUser")
  public ResponseEntity<User> addUser(HttpServletRequest request, @RequestBody String username) {
    Cookie cookie = extractCookie(request);
    if (cookie != null) {

      User newUser = userRepo.createUser(username);
      sessionRepo.addSession(newUser.id, cookie.getValue(), false);
      return ResponseEntity.ok().body(newUser);
    }
    return ResponseEntity.ok().body(null);
  }

}
