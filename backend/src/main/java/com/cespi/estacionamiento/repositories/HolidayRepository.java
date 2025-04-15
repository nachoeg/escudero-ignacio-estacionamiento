package com.cespi.estacionamiento.repositories;

import com.cespi.estacionamiento.models.Holiday;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {

  boolean existsByDate(LocalDate date);

  void deleteByDate(LocalDate date);

  @Query("SELECT h FROM Holiday h WHERE YEAR(h.date) = :year")
  List<Holiday> findByYear(@Param("year") int year);

}