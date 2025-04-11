package com.cespi.estacionamiento.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cespi.estacionamiento.models.LicensePlate;

public interface LicensePlateRepository extends JpaRepository<LicensePlate, String> {

  LicensePlate findByPlate(String plate);

  void deleteByPlate(String plate);

}
