package com.cespi.estacionamiento.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cespi.estacionamiento.dtos.LicensePlateDTO;
import com.cespi.estacionamiento.services.LicensePlateService;
import com.cespi.estacionamiento.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/license-plates", produces = MediaType.APPLICATION_JSON_VALUE)
public class LicensePlateController {

  private final LicensePlateService licensePlateService;
  private final UserService userService;

  public LicensePlateController(LicensePlateService licensePlateService, UserService userService) {
    this.licensePlateService = licensePlateService;
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<LicensePlateDTO> createLicensePlate(@RequestHeader("Authorization") String token,
      @Valid @RequestBody LicensePlateDTO licensePlateDTO) {
    Long userId = userService.getUserIdFromToken(token);
    licensePlateService.createLicensePlate(licensePlateDTO, userId);
    return ResponseEntity.ok(licensePlateDTO);
  }

  @GetMapping
  public List<LicensePlateDTO> getUserLicensePlates(@RequestHeader("Authorization") String token) {
    Long userId = userService.getUserIdFromToken(token);
    return licensePlateService.getUserLicensePlates(userId);
  }

  @DeleteMapping("/{plate}")
  public void deleteLicensePlate(String plate) {
    licensePlateService.deleteLicensePlate(plate);
  }

}
