package com.cespi.estacionamiento.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountGetDTO {

  private double balance;
  private String phone;
  private Long id;

}