package com.indiviragroup.jdt17.spring.rest.api.dto;

import com.indiviragroup.jdt17.spring.rest.api.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    private UUID transactionId;

    private TransactionType transactionType;

    private BigDecimal balanceBefore;

    private BigDecimal balanceAfter;

    private LocalDateTime transactionDate;

    private BigDecimal amount;
}
