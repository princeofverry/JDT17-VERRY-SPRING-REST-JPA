package com.indiviragroup.jdt17.spring.rest.api.services;

import com.indiviragroup.jdt17.spring.rest.api.dto.AccountRequest;
import com.indiviragroup.jdt17.spring.rest.api.dto.AccountResponse;
import com.indiviragroup.jdt17.spring.rest.api.models.Account;
import com.indiviragroup.jdt17.spring.rest.api.models.Customer;
import com.indiviragroup.jdt17.spring.rest.api.repositories.AccountRepositories;
import com.indiviragroup.jdt17.spring.rest.api.repositories.CustomerRepositories;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepositories accountRepository;
    private final CustomerRepositories customerRepository;

    @Transactional
    public AccountResponse create(AccountRequest request) {
        // cari id customer disini king
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Account account = new Account();
        account.setAccountNumber(generateUniqueAccountNumber());
        account.setBalance(request.getBalance() != null ? request.getBalance() : BigDecimal.ZERO);
        account.setCustomer(customer);

        account = accountRepository.save(account);

        return mapToResponse(account);
    }

    public List<AccountResponse> getAll() {
        return accountRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public AccountResponse getById(String id) {
        Account account = accountRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return mapToResponse(account);
    }

    public AccountResponse getByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return mapToResponse(account);
    }

    private String generateUniqueAccountNumber() {
        Random random = new Random();
        String accountNumber;
        do {
            int num = random.nextInt(100000000);
            accountNumber = String.format("%08d", num);
        } while (accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }

//    private String generateUniqueAccountNumber() {
//        Random random = new Random();
//        String accountNumber;
//        do {
//            long num = (long) (random.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
//            accountNumber = String.valueOf(num);
//        } while (accountRepository.existsByAccountNumber(accountNumber));
//        return accountNumber;
//    }

    private AccountResponse mapToResponse(Account account) {
        return new AccountResponse(
                account.getAccountId(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getCustomer() != null ? account.getCustomer().getCustomerId() : null
        );
    }
}
