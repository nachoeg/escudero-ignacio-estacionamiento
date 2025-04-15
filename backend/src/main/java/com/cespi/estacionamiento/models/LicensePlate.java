package com.cespi.estacionamiento.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "license_plates")
public class LicensePlate {

  @Id
  @Column(nullable = false, unique = true)
  @Pattern(regexp = "^[A-Z]{3}[0-9]{3}$|^[A-Z]{2}[0-9]{3}[A-Z]{2}$", message = "Formato de patente inválido")
  // Formatos validos: AAA000, AA000AA.
  private String plate;

  @ManyToMany
  @JoinTable(name = "user_license_plate", joinColumns = @JoinColumn(name = "license_plate_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
  private Set<User> users;

  public LicensePlate(String plate) {
    this.plate = plate.toUpperCase();
    this.users = new HashSet<>();
  }

  public void setPlate(String plate) {
    this.plate = plate.toUpperCase();
  }

  public void addUser(User user) {
    this.users.add(user);
    user.getLicensePlates().add(this);
  }

  public void removeUser(User user) {
    this.users.remove(user);
    user.getLicensePlates().remove(this);
  }

}
