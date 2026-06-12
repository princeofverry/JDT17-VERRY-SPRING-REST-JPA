package com.indiviragroup.jdt17.spring.rest.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AccountRequest {

    @NotNull(message = "Customer ID is required")
    private UUID customerId;

    private BigDecimal balance;
}
