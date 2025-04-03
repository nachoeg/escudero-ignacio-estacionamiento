package com.cespi.estacionamiento.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cespi.estacionamiento.dtos.UserDTO;
import com.cespi.estacionamiento.dtos.UserGetDTO;
import com.cespi.estacionamiento.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<List<UserGetDTO>> getUsers() {
    return ResponseEntity.ok(userService.getUsers());
  }

  @GetMapping("/me")
  public ResponseEntity<UserGetDTO> getCurrentUser(@RequestHeader("Authorization") String token) {
    Long userId = userService.getUserIdFromToken(token);
    return ResponseEntity.ok(userService.getUserById(userId));
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserGetDTO> getUserById(Long id) {
    return ResponseEntity.ok(userService.getUserById(id));
  }

  @PostMapping
  public ResponseEntity<Long> createUser(@Valid @RequestBody UserDTO userDTO) {
    return ResponseEntity.ok(userService.createUser(userDTO));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> updateUser(Long id, @Valid @RequestBody UserDTO userDTO) {
    userService.updateUser(id, userDTO);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(Long id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }

}
