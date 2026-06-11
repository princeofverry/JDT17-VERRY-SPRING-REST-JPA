package com.indiviragroup.jdt17.spring.rest.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawRequest {

    @NotBlank(message = "Account ID is required")
    private String accountId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "10000", message = "Minimum withdraw amount is Rp10.000")
    private BigDecimal amount;
}
