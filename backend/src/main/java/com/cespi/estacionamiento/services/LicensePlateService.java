package com.cespi.estacionamiento.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cespi.estacionamiento.dtos.LicensePlateDTO;
import com.cespi.estacionamiento.models.LicensePlate;
import com.cespi.estacionamiento.models.User;
import com.cespi.estacionamiento.repositories.LicensePlateRepository;
import com.cespi.estacionamiento.repositories.UserRepository;

@Service
public class LicensePlateService {

  private final LicensePlateRepository licensePlateRepository;
  private final UserRepository userRepository;

  public LicensePlateService(LicensePlateRepository licensePlateRepository, UserRepository userRepository) {
    this.licensePlateRepository = licensePlateRepository;
    this.userRepository = userRepository;
  }

  public String createLicensePlate(LicensePlateDTO licensePlateDTO, Long userId) {
    return licensePlateRepository.save(mapToEntity(licensePlateDTO, userId)).getPlate();
  }

  public List<LicensePlateDTO> getUserLicensePlates(Long userId) {
    return licensePlateRepository.findByUserId(userId).stream().map(licensePlate -> mapToDTO(licensePlate)).toList();
  }

  public void deleteLicensePlate(String plate) {
    licensePlateRepository.deleteByPlate(plate);
  }

  public LicensePlateDTO mapToDTO(LicensePlate licensePlate) {
    return new LicensePlateDTO(licensePlate.getPlate());
  }

  public LicensePlate mapToEntity(LicensePlateDTO licensePlateDTO, Long userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    return new LicensePlate(licensePlateDTO.getPlate(), user);
  }

}
