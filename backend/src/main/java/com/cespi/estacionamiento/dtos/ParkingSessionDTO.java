package com.cespi.estacionamiento.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSessionDTO {

  private Long id;
  private String licensePlate;
  private LocalDateTime startTime;
  private LocalDateTime endTime;

}
