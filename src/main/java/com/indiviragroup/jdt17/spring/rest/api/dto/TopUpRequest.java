package com.indiviragroup.jdt17.spring.rest.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TopUpRequest {

    @NotNull
    private String accountId;

    @DecimalMin("10000")
    private BigDecimal amount;
}
