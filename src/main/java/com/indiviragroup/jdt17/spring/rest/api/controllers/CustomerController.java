package com.indiviragroup.jdt17.spring.rest.api.controllers;


import com.indiviragroup.jdt17.spring.rest.api.dto.CustomerRequest;
import com.indiviragroup.jdt17.spring.rest.api.dto.CustomerResponse;
import com.indiviragroup.jdt17.spring.rest.api.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping("/test")
    public String test() {
        return "POST OK";
    }

    // create customer
    @PostMapping("/register")
    public CustomerResponse create(
            @Valid @RequestBody CustomerRequest request
    ) {
        return service.create(request);
    }

    // get all
    @GetMapping
    public List<CustomerResponse> getAll() {
        return service.getAll();
    }

    // get by id
    @GetMapping("/{id}")
    public CustomerResponse getById(
            @PathVariable UUID id
    ) {
        return service.getById(id);
    }

    @PostMapping("/{id}")
    public CustomerResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody CustomerRequest request
    ) {
        return service.update(id, request);
    }

    @PostMapping("/{id}/delete")
    public String delete(
            @PathVariable UUID id
    ) {
        service.delete(id);
        return "customer deleted";
    }
}
