package com.cespi.estacionamiento.models;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

  @Id
  @Column(nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  @NotBlank
  // formato: 10 digitos sin considerar el 0 o el 15 al inicio
  @Pattern(regexp = "^[0-9]{10}$", message = "Formato de teléfono inválido")
  private String phone;

  @Column(nullable = false)
  @NotBlank
  private String password;

  @Column(nullable = false, unique = true)
  @NotBlank
  @Email
  private String email;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  private Account account;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<LicensePlate> licensePlates;

  public User(String email, String phone, String password) {
    this.email = email;
    this.phone = phone;
    this.password = password;
    this.account = new Account(this);
  }

  public void addLicensePlate(LicensePlate licensePlate) {
    licensePlates.add(licensePlate);
  }

}
