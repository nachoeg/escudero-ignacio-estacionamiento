package com.cespi.estacionamiento.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cespi.estacionamiento.dtos.HolidayGetDTO;
import com.cespi.estacionamiento.services.HolidayService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/api/holidays", produces = "application/json")
public class HolidayController {

  private final HolidayService holidayService;

  public HolidayController(HolidayService holidayService) {
    this.holidayService = holidayService;
  }

  @GetMapping
  public ResponseEntity<List<HolidayGetDTO>> getHolidays() {
    return ResponseEntity.ok(holidayService.getHolidays());
  }

  @PostMapping
  public ResponseEntity<Void> addHoliday(@Valid @RequestBody HolidayGetDTO holiday) {
    holidayService.addHoliday(holiday);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

}
