package com.indiviragroup.jdt17.spring.rest.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CustomerResponse {
    private UUID id;

    private String name;

    private String email;

    private String phone;

    private String address;
}
