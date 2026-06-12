package com.indiviragroup.jdt17.spring.rest.api.controllers;

import com.indiviragroup.jdt17.spring.rest.api.dto.TopUpRequest;
import com.indiviragroup.jdt17.spring.rest.api.dto.TransactionResponse;
import com.indiviragroup.jdt17.spring.rest.api.dto.TransferRequest;
import com.indiviragroup.jdt17.spring.rest.api.dto.WithdrawRequest;
import com.indiviragroup.jdt17.spring.rest.api.services.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transactions/topup")
    public TransactionResponse topUp(@Valid @RequestBody TopUpRequest request) {
        return transactionService.topUp(request);
    }

    @PostMapping("/transactions/transfer")
    public List<TransactionResponse> transfer(@Valid @RequestBody TransferRequest request) {
        return transactionService.transfer(request);
    }

    @PostMapping("/transactions/withdraw")
    public TransactionResponse withdraw(@Valid @RequestBody WithdrawRequest request) {
        return transactionService.withdraw(request);
    }

    @GetMapping("/transactions")
    public Page<TransactionResponse> getAllTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "transactionDate") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction
    ) {
        return transactionService.getAllTransactions(page, size, sortBy, direction);
    }

    @GetMapping("/transactions/account/{accountId}")
    public Page<TransactionResponse> getTransactionsByAccount(
            @PathVariable String accountId,
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "transactionDate") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction
    ) {
        return transactionService.getTransactionsByAccount(accountId, type, page, size, sortBy, direction);
    }
}
