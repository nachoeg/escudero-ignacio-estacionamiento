package com.cespi.estacionamiento.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cespi.estacionamiento.dtos.TransactionDTO;
import com.cespi.estacionamiento.dtos.TransactionGetDTO;
import com.cespi.estacionamiento.services.AccountService;
import com.cespi.estacionamiento.services.UserService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(value = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

  private final AccountService accountService;
  private final UserService userService;

  public AccountController(AccountService accountService, UserService userService) {
    this.accountService = accountService;
    this.userService = userService;
  }

  @PostMapping("/add-credit")
  public ResponseEntity<?> addCredit(@RequestHeader("Authorization") String token,
      @Valid @RequestBody TransactionDTO transaction) {
    if (transaction.getAmount() < 100) {
      return ResponseEntity.badRequest().body("El monto mínimo a cargar es de 100");
    }
    Long userId = userService.getUserIdFromToken(token);
    transaction.setDescription("Carga de crédito");
    accountService.addTransaction(userId, transaction);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/transactions-history")
  public List<TransactionGetDTO> getTransactions(@RequestHeader("Authorization") String token) {
    Long userId = userService.getUserIdFromToken(token);
    return accountService.getTransactions(userId);
  }

}
