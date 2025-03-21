package com.ea.backend.infra.http;

import com.ea.backend.infra.http.model.UserResponse;
import com.ea.backend.infra.security.UserAuthenticated;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @GetMapping("/me")
  public ResponseEntity<?> getAuthenticatedUser(HttpServletRequest request) {

    var authenticated = (UserAuthenticated) request.getAttribute("user");

    var user = authenticated.getUser();

    return ResponseEntity.ok()
        .body(
            new UserResponse(
                user.getId().toString(), user.getName(), user.getEmail(), user.getRole()));
  }
}
