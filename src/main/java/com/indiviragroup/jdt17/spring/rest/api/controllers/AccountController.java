package com.indiviragroup.jdt17.spring.rest.api.controllers;

import com.indiviragroup.jdt17.spring.rest.api.dto.AccountRequest;
import com.indiviragroup.jdt17.spring.rest.api.dto.AccountResponse;
import com.indiviragroup.jdt17.spring.rest.api.services.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public AccountResponse create(@Valid @RequestBody AccountRequest request) {
        return accountService.create(request);
    }

    @GetMapping
    public List<AccountResponse> getAll() {
        return accountService.getAll();
    }

    @GetMapping("/{id}")
    public AccountResponse getById(@PathVariable String id) {
        return accountService.getById(id);
    }

    @GetMapping("/account-number/{accountNumber}")
    public AccountResponse getByAccountNumber(@PathVariable String accountNumber) {
        return accountService.getByAccountNumber(accountNumber);
    }
}
