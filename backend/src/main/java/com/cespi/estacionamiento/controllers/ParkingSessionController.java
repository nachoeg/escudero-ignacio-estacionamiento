package com.cespi.estacionamiento.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cespi.estacionamiento.dtos.LicensePlateDTO;
import com.cespi.estacionamiento.dtos.ParkingSessionDTO;
import com.cespi.estacionamiento.services.ParkingSessionService;
import com.cespi.estacionamiento.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/parking-sessions", produces = MediaType.APPLICATION_JSON_VALUE)
public class ParkingSessionController {

  private final ParkingSessionService parkingSessionService;
  private final UserService userService;

  public ParkingSessionController(ParkingSessionService parkingSessionService, UserService userService) {
    this.parkingSessionService = parkingSessionService;
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<?> startSession(@RequestHeader("Authorization") String token,
      @Valid @RequestBody LicensePlateDTO request) {
    try {
      Long userId = userService.getUserIdFromToken(token);
      ParkingSessionDTO session = parkingSessionService.startSession(userId, request.getPlate());
      return ResponseEntity.status(HttpStatus.CREATED).body(session);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{sessionId}/stop")
  public ResponseEntity<?> stopSession(@PathVariable Long sessionId, @RequestHeader("Authorization") String token) {
    try {
      Long userId = userService.getUserIdFromToken(token);
      ParkingSessionDTO session = parkingSessionService.stopSession(sessionId, userId);
      return ResponseEntity.ok(session);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

}
