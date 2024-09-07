package org.example;
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
