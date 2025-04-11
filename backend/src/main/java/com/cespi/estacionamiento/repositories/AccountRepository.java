package com.cespi.estacionamiento.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cespi.estacionamiento.models.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

  Account findByUserPhone(String phone);

  Account findByUserId(Long userId);

}
