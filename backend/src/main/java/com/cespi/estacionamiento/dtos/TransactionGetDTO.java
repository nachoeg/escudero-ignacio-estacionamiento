package com.cespi.estacionamiento.dtos;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransactionGetDTO extends TransactionDTO {

  private Long id;
  private LocalDateTime timestamp;

  public TransactionGetDTO(Long id, Double amount, String description, LocalDateTime timestamp) {
    super(amount, description);
    this.id = id;
    this.timestamp = timestamp;
  }

}
