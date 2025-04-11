package com.cespi.estacionamiento.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cespi.estacionamiento.configs.OperatingHoursConfig;
import com.cespi.estacionamiento.dtos.ParkingSessionDTO;
import com.cespi.estacionamiento.dtos.TransactionDTO;
import com.cespi.estacionamiento.models.LicensePlate;
import com.cespi.estacionamiento.models.ParkingSession;
import com.cespi.estacionamiento.models.User;
import com.cespi.estacionamiento.repositories.LicensePlateRepository;
import com.cespi.estacionamiento.repositories.ParkingSessionRepository;
import com.cespi.estacionamiento.repositories.UserRepository;

import java.time.LocalDateTime;

@Service
public class ParkingSessionService {

  private final ParkingSessionRepository parkingSessionRepository;
  private final UserRepository userRepository;
  private final LicensePlateRepository licensePlateRepository;
  private final AccountService accountService;
  private final OperatingHoursConfig operatingHoursConfig;

  public ParkingSessionService(ParkingSessionRepository parkingSessionRepository, UserRepository userRepository,
      LicensePlateRepository licensePlateRepository, AccountService accountService,
      OperatingHoursConfig operatingHoursConfig) {
    this.parkingSessionRepository = parkingSessionRepository;
    this.userRepository = userRepository;
    this.licensePlateRepository = licensePlateRepository;
    this.accountService = accountService;
    this.operatingHoursConfig = operatingHoursConfig;
  }

  @Transactional
  public ParkingSessionDTO startSession(Long userId, String licensePlateNumber) {

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    Optional<ParkingSession> activeSession = parkingSessionRepository.findByUserIdAndEndTimeIsNull(userId);
    if (activeSession.isPresent()) {
      throw new RuntimeException("El usuario ya tiene una sesión de estacionamiento activa");
    }

    LicensePlate licensePlate = licensePlateRepository.findByPlate(licensePlateNumber);
    if (licensePlate == null) {
      throw new RuntimeException("La placa de licencia no existe");
    }

    if (!user.getLicensePlates().contains(licensePlate)) {
      throw new RuntimeException("La placa de licencia no pertenece al usuario");
    }

    if (user.getAccount().getBalance() <= 0) {
      throw new RuntimeException("El usuario no tiene saldo suficiente");
    }

    // if (!operatingHoursConfig.isWithinOperatingHours(LocalTime.now())) {
    // System.out.println("Fuera del horario de operación (" +
    // operatingHoursConfig.getStart() + " - " +
    // operatingHoursConfig.getEnd() + ")");
    // throw new RuntimeException("Fuera del horario de operación (" +
    // operatingHoursConfig.getStart() + " - " +
    // operatingHoursConfig.getEnd() + ")");
    // }

    ParkingSession session = new ParkingSession(user, licensePlate);
    parkingSessionRepository.save(session);

    return new ParkingSessionDTO(
        session.getId(),
        session.getLicensePlate().getPlate(),
        session.getStartTime(),
        null);
  }

  @Transactional
  public ParkingSessionDTO stopSession(Long sessionId, Long userId) {
    ParkingSession session = parkingSessionRepository.findByIdAndUserId(sessionId, userId)
        .orElseThrow(() -> new RuntimeException("Sesión de estacionamiento no encontrada"));

    if (!session.isActive()) {
      throw new RuntimeException("La sesión de estacionamiento ya está finalizada");
    }

    session.endSession();
    parkingSessionRepository.save(session);

    TransactionDTO transactionDTO = new TransactionDTO(getTotalCost(session), "Pago por estacionamiento - Patente: "
        + session.getLicensePlate().getPlate());
    accountService.addTransaction(userId, transactionDTO);

    return new ParkingSessionDTO(
        session.getId(),
        session.getLicensePlate().getPlate(),
        session.getStartTime(),
        session.getEndTime());
  }

  private double getTotalCost(ParkingSession session) {
    LocalDateTime startTime = session.getStartTime();
    LocalDateTime endTime = session.getEndTime();
    LocalDateTime operationalEndTime = LocalDateTime.of(
        startTime.toLocalDate(),
        operatingHoursConfig.getEndTime());
    LocalDateTime adjustedEndTime = endTime.isAfter(operationalEndTime) ? operationalEndTime : endTime;

    long durationInMinutes = java.time.Duration.between(startTime, adjustedEndTime).toMinutes();

    double costPerFraction = 2.50;
    long fractions = (durationInMinutes + 14) / 15;
    return Math.round(fractions * costPerFraction * 100.0) / 100.0;
  }
}
