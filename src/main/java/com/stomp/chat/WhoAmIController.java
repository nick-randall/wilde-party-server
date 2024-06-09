package com.stomp.chat;

import org.apache.catalina.connector.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
public class WhoAmIController {

  final SessionRepo sessionRepo = new SessionRepo(Database.getInstance());
  final UserRepo userRepo = new UserRepo(Database.getInstance());

  @GetMapping("/session")
  public ResponseEntity<String> getSession(HttpServletResponse response, HttpServletRequest request) {
    Cookie[] existingCookies = request.getCookies();
    System.out.println("eating cookies");
    if (existingCookies == null) {
      System.out.println("No cookie");
      UUID uuid = UUID.randomUUID();
      Cookie cookie = new Cookie("SESSION", uuid.toString());
      response.addCookie(cookie);
      System.out.println("Sending new cookie: " + cookie.getValue());

    } else {
      System.out.println("existing cookie:" + existingCookies[0].getValue());
      // int userId = sessionRepo.getUserIdFromSessionToken(token);
      // System.out.println(userId);
      // if (userId == -1) {
      //   UnnamedUser user = new UnnamedUser(token);
      //   accessor.setUser(user);
      // } else {
      //   System.out.println("adding user to accessor");
      //   User user = userRepo.getUser(userId);
      //   accessor.setUser(user);
      // }
    }
    return ResponseEntity.ok().body(null);

  }

  @PostMapping("/whoami")
  public ResponseEntity<User> whoAmI(HttpServletResponse response, HttpServletRequest request) {
    Cookie[] existingCookies = request.getCookies();
    System.out.println("whoami existing cookies: " + existingCookies);
    if (existingCookies != null) {

      int userId = sessionRepo.getUserIdFromSessionToken(existingCookies[0].getValue());
      System.out.println(userId);
      if (userId != -1) {
        User user = userRepo.getUser(userId);
        return ResponseEntity.ok().body(user);
      } else {

      }
    }
    return ResponseEntity.ok().body(null);
  }

  @PostMapping("/addUser")
  public ResponseEntity<User> addUser(HttpServletRequest request, @RequestBody String username) {
    System.out.println("add user " + username);
    Cookie[] cookies = request.getCookies();
    if (cookies != null && cookies.length > 0) {

      User newUser = userRepo.createUser(username);
      sessionRepo.addSession(newUser.id, cookies[0].getValue());
      return ResponseEntity.ok().body(newUser);
    }
    return ResponseEntity.ok().body(null);
  }

}
