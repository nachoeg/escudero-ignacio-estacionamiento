package com.cespi.estacionamiento.services;

import org.springframework.stereotype.Service;

import com.cespi.estacionamiento.dtos.HolidayGetDTO;
import com.cespi.estacionamiento.models.Holiday;
import com.cespi.estacionamiento.repositories.HolidayRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class HolidayService {

  private HolidayRepository holidayRepository;

  public HolidayService(HolidayRepository holidayRepository) {
    this.holidayRepository = holidayRepository;
  }

  public List<HolidayGetDTO> getHolidays() {
    return holidayRepository.findAll().stream()
        .map(holiday -> new HolidayGetDTO(holiday.getName(), holiday.getDate()))
        .toList();
  }

  public void addHoliday(HolidayGetDTO holidayDTO) {
    Holiday holiday = new Holiday(holidayDTO.getName(), holidayDTO.getDate());
    holidayRepository.save(holiday);
  }

  public void deleteHoliday(LocalDate date) {
    holidayRepository.deleteByDate(date);
  }

}