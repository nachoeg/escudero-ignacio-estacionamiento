package com.cespi.estacionamiento.services;

import org.springframework.stereotype.Service;

import com.cespi.estacionamiento.dtos.LicensePlateDTO;
import com.cespi.estacionamiento.models.LicensePlate;
import com.cespi.estacionamiento.models.User;
import com.cespi.estacionamiento.repositories.LicensePlateRepository;
import com.cespi.estacionamiento.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class LicensePlateService {

  private final LicensePlateRepository licensePlateRepository;
  private final UserRepository userRepository;

  public LicensePlateService(LicensePlateRepository licensePlateRepository, UserRepository userRepository) {
    this.licensePlateRepository = licensePlateRepository;
    this.userRepository = userRepository;
  }

  @Transactional
  public String createLicensePlate(LicensePlateDTO licensePlateDTO, Long userId) {
    return licensePlateRepository.save(mapToEntity(licensePlateDTO, userId)).getPlate();
  }

  public void deleteLicensePlate(String plate) {
    licensePlateRepository.deleteByPlate(plate);
  }

  public LicensePlate mapToEntity(LicensePlateDTO licensePlateDTO, Long userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    LicensePlate licensePlate = licensePlateRepository.findByPlate(licensePlateDTO.getPlate());
    if (licensePlate == null) {
      licensePlate = new LicensePlate(licensePlateDTO.getPlate());
    }
    licensePlate.addUser(user);
    return licensePlate;
  }

}
