package com.cespi.estacionamiento.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "operating-hours")
public class OperatingHoursConfig {
  private String start;
  private String end;

  public LocalTime getStartTime() {
    return LocalTime.parse(start);
  }

  public LocalTime getEndTime() {
    return LocalTime.parse(end);
  }

  public boolean isWithinOperatingHours(LocalTime time) {
    return !time.isBefore(getStartTime()) && !time.isAfter(getEndTime());
  }
}