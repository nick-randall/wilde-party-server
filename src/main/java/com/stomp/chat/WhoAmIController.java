package com.stomp.chat;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

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

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(HttpServletResponse response, HttpServletRequest request) {
    System.out.println("logging out");
    Cookie existingCookie = extractCookie(request);
    if (existingCookie != null) {
      existingCookie.setMaxAge(0);
      response.addCookie(existingCookie);
    }
    return ResponseEntity.ok(null);
  }

  @PostMapping("/whoami")
  public ResponseEntity<User> whoAmI(HttpServletResponse response, HttpServletRequest request) {

    Cookie existingCookie = extractCookie(request);

    if (existingCookie == null) {
      // New user with no session cookie
      return ResponseEntity.noContent().build();
    }

    Session existingSession = sessionRepo.getSessionFromSessionToken(existingCookie.getValue());
    if (existingSession == null) {
      // Error -- user not found!
      return ResponseEntity.notFound().build();
    }
    User user = userRepo.getUser(existingSession.userId);
    if (user == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().body(user);
  }

  @PostMapping("/addUser")
  public ResponseEntity<User> addUser(HttpServletResponse response, HttpServletRequest request,
      @RequestBody AddUserRequest addUserRequest) {
    System.out.println(addUserRequest);
    // Cookie cookie = extractCookie(request);
    // if (cookie != null) {
    System.out.println(addUserRequest);
    User newUser = userRepo.createUser(addUserRequest.getUsername());
    Cookie newCookie = createCookie();
    sessionRepo.addSession(newUser.id, newCookie.getValue());
    response.addCookie(newCookie);
    return ResponseEntity.ok().body(newUser);
    // }
    // return ResponseEntity.ok().body(null);
  }

}
