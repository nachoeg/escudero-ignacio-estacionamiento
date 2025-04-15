package com.cespi.estacionamiento.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "accounts")
public class Account {

  @Id
  @Column(nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
  private List<Transaction> transactions;

  @OneToOne
  @JoinColumn(name = "phone", referencedColumnName = "phone")
  private User user;

  @Column(nullable = false)
  private double balance;

  public Account(User user) {
    this.user = user;
    this.transactions = new ArrayList<>();
    this.balance = 0;
  }

  public void addTransaction(Transaction transaction) {
    transactions.add(transaction);
    balance += transaction.getAmount();
  }

}
