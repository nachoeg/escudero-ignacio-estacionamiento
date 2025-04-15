package com.cespi.estacionamiento.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HolidayDTO {

  private String fecha;
  private String nombre;
  private String tipo;

}
