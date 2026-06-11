package com.indiviragroup.jdt17.spring.rest.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {

    @NotBlank
    private String sourceAccountId;

    @NotBlank
    private String destinationAccountId;

    @DecimalMin("10000")
    private BigDecimal amount;


}
