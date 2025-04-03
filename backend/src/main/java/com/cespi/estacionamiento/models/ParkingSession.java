package com.cespi.estacionamiento.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ParkingSession {

  @Id
  @Column(nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_phone")
  private User user;

  @ManyToOne
  @JoinColumn(name = "plate")
  private LicensePlate licensePlate;

  @Column(nullable = false)
  private LocalDateTime startTime;

  @Column
  private LocalDateTime endTime;

  public ParkingSession(User user, LicensePlate licensePlate) {
    this.user = user;
    this.licensePlate = licensePlate;
    this.startTime = LocalDateTime.now();
  }

  public void endSession() {
    this.endTime = LocalDateTime.now();
  }

}
