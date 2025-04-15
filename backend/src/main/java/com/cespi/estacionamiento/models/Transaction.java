package com.cespi.estacionamiento.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction {

  @Id
  @Column(nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;

  @Column(nullable = false)
  private LocalDateTime timestamp;

  @Column(nullable = false)
  private Double amount;

  @Column
  private String description;

  public Transaction(Account account, Double amount, String description) {
    this.account = account;
    this.amount = amount;
    this.timestamp = LocalDateTime.now();
    this.description = description;
  }

}
