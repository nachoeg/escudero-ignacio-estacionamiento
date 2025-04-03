package com.cespi.estacionamiento.services;

import org.springframework.stereotype.Service;

import com.cespi.estacionamiento.models.Account;
import com.cespi.estacionamiento.models.Transaction;
import com.cespi.estacionamiento.models.User;
import com.cespi.estacionamiento.repositories.AccountRepository;

@Service
public class AccountService {

  private AccountRepository accountRepository;

  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public Long createAccount(User user) {
    return accountRepository.save(new Account(user)).getId();
  }

  public double getBalance(String phone) {
    return accountRepository.findByUserPhone(phone).getBalance();
  }

  public void addTransaction(String phone, double amount) {
    Account account = accountRepository.findByUserPhone(phone);
    Transaction transaction = new Transaction(account, amount);
    account.addTransaction(transaction);
    accountRepository.save(account);
  }

}
