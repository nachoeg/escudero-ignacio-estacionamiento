package com.cespi.estacionamiento.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cespi.estacionamiento.models.ParkingSession;

public interface ParkingSessionRepository extends JpaRepository<ParkingSession, Long> {

  Optional<ParkingSession> findByUserIdAndEndTimeIsNull(Long userId);

  Optional<ParkingSession> findByIdAndUserId(Long id, Long userId);

  List<ParkingSession> findByUserIdOrderByStartTimeDesc(Long userId);

  Optional<ParkingSession> findByLicensePlatePlateAndEndTimeIsNull(String licensePlate);
}