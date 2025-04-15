package com.cespi.estacionamiento.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserGetDTO {

  private Long id;
  private String email;
  private String phone;
  private double balance;
  private List<String> licensePlates;
  private boolean parkingSessionActive;
  private ParkingSessionDTO parkingSession;

}