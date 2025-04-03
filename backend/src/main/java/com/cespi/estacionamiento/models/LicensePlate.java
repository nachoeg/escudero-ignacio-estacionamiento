package com.cespi.estacionamiento.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class LicensePlate {

  @Id
  @Column(nullable = false, unique = true)
  @Pattern(regexp = "^[A-Z]{3}[0-9]{3}$|^[A-Z]{2}[0-9]{3}[A-Z]{2}$", message = "Formato de patente inválido")
  // Formatos validos: AAA000, AA000AA.
  private String plate;

  @ManyToOne
  @JoinColumn(name = "user_phone")
  private @Setter User user;

  public LicensePlate(String plate, User user) {
    this.plate = plate.toUpperCase();
    this.user = user;
    user.addLicensePlate(this);
  }

  public void setPlate(String plate) {
    this.plate = plate.toUpperCase();
  }

}
