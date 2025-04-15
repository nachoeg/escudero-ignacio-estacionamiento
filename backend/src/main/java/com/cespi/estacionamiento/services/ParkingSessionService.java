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
import com.cespi.estacionamiento.repositories.HolidayRepository;
import com.cespi.estacionamiento.repositories.LicensePlateRepository;
import com.cespi.estacionamiento.repositories.ParkingSessionRepository;
import com.cespi.estacionamiento.repositories.UserRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class ParkingSessionService {

  private final ParkingSessionRepository parkingSessionRepository;
  private final UserRepository userRepository;
  private final LicensePlateRepository licensePlateRepository;
  private final AccountService accountService;
  private final OperatingHoursConfig operatingHoursConfig;
  private final HolidayRepository holidayRepository;

  public ParkingSessionService(ParkingSessionRepository parkingSessionRepository, UserRepository userRepository,
      LicensePlateRepository licensePlateRepository, AccountService accountService,
      OperatingHoursConfig operatingHoursConfig, HolidayRepository holidayRepository) {
    this.parkingSessionRepository = parkingSessionRepository;
    this.userRepository = userRepository;
    this.licensePlateRepository = licensePlateRepository;
    this.accountService = accountService;
    this.operatingHoursConfig = operatingHoursConfig;
    this.holidayRepository = holidayRepository;
  }

  @Transactional
  public ParkingSessionDTO startSession(Long userId, String licensePlateNumber) {

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    Optional<ParkingSession> activeSession = parkingSessionRepository.findByUserIdAndEndTimeIsNull(userId);
    if (activeSession.isPresent()) {
      throw new RuntimeException("El usuario ya tiene una sesión de estacionamiento activa");
    }

    Optional<ParkingSession> activeLicensePlateSession = parkingSessionRepository
        .findByLicensePlatePlateAndEndTimeIsNull(licensePlateNumber);
    if (activeLicensePlateSession.isPresent()) {
      throw new RuntimeException("La patente ya tiene una sesión de estacionamiento activa");
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

    if (!operatingHoursConfig.isWithinOperatingHours(LocalTime.now())) {
      throw new RuntimeException("Fuera del horario de operación (" +
          operatingHoursConfig.getStart() + " - " +
          operatingHoursConfig.getEnd() + ")");
    }

    if (LocalDate.now().getDayOfWeek().getValue() >= 6) {
      throw new RuntimeException("No se puede iniciar una sesión de estacionamiento en fines de semana");
    }

    if (holidayRepository.existsByDate(LocalDate.now())) {
      throw new RuntimeException("No se puede iniciar una sesión de estacionamiento en días feriados");
    }

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

    TransactionDTO transactionDTO = new TransactionDTO(-getTotalCost(session), "Pago por estacionamiento - Patente: "
        + session.getLicensePlate().getPlate());
    accountService.addTransaction(userId, transactionDTO);

    return new ParkingSessionDTO(
        session.getId(),
        session.getLicensePlate().getPlate(),
        session.getStartTime(),
        session.getEndTime());
  }

  /**
   * Calculates the total cost of the parking session.
   * It is based on the session duration and the cost per 15-minute fraction.
   * If the session extends beyond operating hours, the end time is adjusted.
   * The cost is rounded to two decimal places.
   * 
   * @param session
   * @return Total cost of the parking session.
   */
  private double getTotalCost(ParkingSession session) {
    LocalDateTime startTime = session.getStartTime();
    LocalDateTime endTime = session.getEndTime();
    LocalDateTime operationalEndTime = LocalDateTime.of(
        startTime.toLocalDate(),
        operatingHoursConfig.getEndTime());
    LocalDateTime adjustedEndTime = endTime.isAfter(operationalEndTime) ? operationalEndTime : endTime;
    long durationInMinutes = Math.max(Duration.between(startTime, adjustedEndTime).toMinutes(), 1);
    double costPerFraction = 2.50;
    long fractions = (durationInMinutes + 14) / 15;
    double result = Math.round(fractions * costPerFraction * 100.0) / 100.0;
    return result;
  }
}
