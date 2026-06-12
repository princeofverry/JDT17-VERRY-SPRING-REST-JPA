package com.indiviragroup.jdt17.spring.rest.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private UUID accountId;
    private String accountNumber;
    private BigDecimal balance;
    private UUID customerId;
}
