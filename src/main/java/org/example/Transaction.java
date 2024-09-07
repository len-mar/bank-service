package org.example;
// todo:
//  transaction has an amount, a balance (new account balance after the transaction), a description (optional), and a date.
//  The transactions should be implemented as records.
//  To determine the current account balance, the BankService should return the balance of the last transaction.

import lombok.Builder;
import lombok.ToString;
import lombok.With;

import java.math.BigDecimal;
import java.security.DrbgParameters;
import java.time.Instant;
import java.time.ZonedDateTime;

@Builder
@With
public record Transaction(TransactionType type, BigDecimal amount, BigDecimal newBalance, Instant timestamp) {
    public Transaction(TransactionType type, BigDecimal amount, BigDecimal newBalance){
        this(type, amount, newBalance, Instant.now());
    }

}
