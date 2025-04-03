package com.cespi.estacionamiento.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cespi.estacionamiento.dtos.UserDTO;
import com.cespi.estacionamiento.models.User;
import com.cespi.estacionamiento.services.AuthService;
import com.cespi.estacionamiento.services.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

  private final AuthService authService;
  private final UserService userService;

  public AuthController(AuthService authService, UserService userService) {
    this.authService = authService;
    this.userService = userService;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
    try {
      User user = authService.authenticate(loginRequest.getPhone(), loginRequest.getPassword());
      String token = authService.generateToken(user.getId());

      return ResponseEntity.ok(new LoginResponse(token));
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
    }
  }

  @PostMapping("/register")
  public ResponseEntity<Long> registerUser(@Valid @RequestBody UserDTO userDTO) {
    return ResponseEntity.ok(userService.createUser(userDTO));
  }

  @Setter
  @Getter
  public static class LoginRequest {
    private String phone;
    private String password;
  }

  @Getter
  @Setter
  @AllArgsConstructor
  public static class LoginResponse {
    private String token;
  }

}
