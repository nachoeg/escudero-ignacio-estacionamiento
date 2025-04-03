package com.cespi.estacionamiento.dtos;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserGetDTO extends UserDTO {

  private Long id;
  private double balance;
  private List<String> licensePlates;

  public UserGetDTO(String email, String phone, Long id, double balance,
      List<String> licensePlates) {
    super(email, phone, null);
    this.id = id;
    this.balance = balance;
    this.licensePlates = licensePlates;
  }

}