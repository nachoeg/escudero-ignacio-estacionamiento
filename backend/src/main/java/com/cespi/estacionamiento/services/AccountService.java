package com.cespi.estacionamiento.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cespi.estacionamiento.dtos.TransactionDTO;
import com.cespi.estacionamiento.models.Account;
import com.cespi.estacionamiento.models.Transaction;
import com.cespi.estacionamiento.repositories.AccountRepository;

@Service
public class AccountService {

  private AccountRepository accountRepository;

  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public double getBalance(String phone) {
    return accountRepository.findByUserPhone(phone).getBalance();
  }

  @Transactional
  public void addTransaction(Long userId, TransactionDTO transactionDTO) {
    Account account = accountRepository.findByUserId(userId);
    if (account == null) {
      throw new IllegalArgumentException("Account not found for user ID: " + userId);
    }
    Transaction transaction = new Transaction(account, transactionDTO.getAmount(), transactionDTO.getDescription());
    account.addTransaction(transaction);
    accountRepository.save(account);
  }

}
