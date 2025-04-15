package com.cespi.estacionamiento.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cespi.estacionamiento.dtos.UserGetDTO;
import com.cespi.estacionamiento.services.UserService;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/me")
  public ResponseEntity<UserGetDTO> getCurrentUser(@RequestHeader("Authorization") String token) {
    Long userId = userService.getUserIdFromToken(token);
    return ResponseEntity.ok(userService.getUserById(userId));
  }

}
