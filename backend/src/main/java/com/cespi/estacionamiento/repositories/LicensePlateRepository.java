package com.cespi.estacionamiento.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cespi.estacionamiento.models.LicensePlate;

public interface LicensePlateRepository extends JpaRepository<LicensePlate, Long> {

  LicensePlate findByPlate(String plate);

  List<LicensePlate> findByUserId(Long userId);

  void deleteByPlate(String plate);

}
