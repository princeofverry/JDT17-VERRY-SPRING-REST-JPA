package com.indiviragroup.jdt17.spring.rest.api.services;

import com.indiviragroup.jdt17.spring.rest.api.dto.TopUpRequest;
import com.indiviragroup.jdt17.spring.rest.api.dto.TransferRequest;
import com.indiviragroup.jdt17.spring.rest.api.dto.WithdrawRequest;
import com.indiviragroup.jdt17.spring.rest.api.dto.TransactionResponse;
import com.indiviragroup.jdt17.spring.rest.api.models.Account;
import com.indiviragroup.jdt17.spring.rest.api.models.Transaction;
import com.indiviragroup.jdt17.spring.rest.api.enums.TransactionType;
import com.indiviragroup.jdt17.spring.rest.api.repositories.AccountRepositories;
import com.indiviragroup.jdt17.spring.rest.api.repositories.TransactionRepositories;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepositories transactionRepository;
    private final AccountRepositories accountRepository;

    @Value("${bank.transfer.admin-fee}")
    private BigDecimal adminFee;

    @Transactional
    public TransactionResponse topUp(TopUpRequest request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.valueOf(10000)) < 0) {
            throw new RuntimeException("Minimum top up amount is Rp10.000");
        }

        BigDecimal before = account.getBalance();
        BigDecimal after = before.add(request.getAmount());

        account.setBalance(after);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.TOPUP);
        transaction.setAmount(request.getAmount());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setDestinationAccount(account);
        transaction.setBalanceBefore(before);
        transaction.setBalanceAfter(after);

        transaction = transactionRepository.save(transaction);

        return mapToResponse(transaction);
    }

    @Transactional
    public List<TransactionResponse> transfer(TransferRequest request) {
        Account source = accountRepository.findById(request.getSourceAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Account destination = accountRepository.findById(request.getDestinationAccountId())
                .orElseThrow(() -> new RuntimeException("Destination account not found"));

        if (source.getAccountId().equals(destination.getAccountId())) {
            throw new RuntimeException("Cannot transfer to same account");
        }

        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.valueOf(10000)) < 0) {
            throw new RuntimeException("Minimum transfer amount is Rp10.000");
        }

        BigDecimal totalDeduction = request.getAmount().add(adminFee);
        if (source.getBalance().compareTo(totalDeduction) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        BigDecimal sourceBefore = source.getBalance();
        BigDecimal sourceAfter = sourceBefore.subtract(totalDeduction);
        source.setBalance(sourceAfter);
        accountRepository.save(source);

        BigDecimal destBefore = destination.getBalance();
        BigDecimal destAfter = destBefore.add(request.getAmount());
        destination.setBalance(destAfter);
        accountRepository.save(destination);

        LocalDateTime now = LocalDateTime.now();

        Transaction transferOut = new Transaction();
        transferOut.setTransactionType(TransactionType.TRANSFER_OUT);
        transferOut.setAmount(request.getAmount());
        transferOut.setTransactionDate(now);
        transferOut.setSourceAccount(source);
        transferOut.setDestinationAccount(destination);
        transferOut.setBalanceBefore(sourceBefore);
        transferOut.setBalanceAfter(sourceAfter);
        transferOut = transactionRepository.save(transferOut);

        Transaction transferIn = new Transaction();
        transferIn.setTransactionType(TransactionType.TRANSFER_IN);
        transferIn.setAmount(request.getAmount());
        transferIn.setTransactionDate(now);
        transferIn.setSourceAccount(source);
        transferIn.setDestinationAccount(destination);
        transferIn.setBalanceBefore(destBefore);
        transferIn.setBalanceAfter(destAfter);
        transferIn = transactionRepository.save(transferIn);

        return List.of(mapToResponse(transferOut), mapToResponse(transferIn));
    }

    @Transactional
    public TransactionResponse withdraw(WithdrawRequest request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.valueOf(10000)) < 0) {
            throw new RuntimeException("Minimum withdraw amount is Rp10.000");
        }

        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        BigDecimal before = account.getBalance();
        BigDecimal after = before.subtract(request.getAmount());

        account.setBalance(after);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.WITHDRAW);
        transaction.setAmount(request.getAmount());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setSourceAccount(account);
        transaction.setBalanceBefore(before);
        transaction.setBalanceAfter(after);

        transaction = transactionRepository.save(transaction);

        return mapToResponse(transaction);
    }

    public Page<TransactionResponse> getAllTransactions(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return transactionRepository.findAll(pageable).map(this::mapToResponse);
    }

    public Page<TransactionResponse> getTransactionsByAccount(String accountId, String type, int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        if (type != null && !type.equalsIgnoreCase("ALL") && !type.isBlank()) {
            TransactionType filterType = TransactionType.valueOf(type.toUpperCase());
            return transactionRepository.findByAccountAndType(accountId, filterType, pageable).map(this::mapToResponse);
        }

        return transactionRepository.findByAccount(accountId, pageable).map(this::mapToResponse);
    }

    private TransactionResponse mapToResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getTransactionId(),
                transaction.getTransactionType(),
                transaction.getBalanceBefore(),
                transaction.getBalanceAfter(),
                transaction.getTransactionDate(),
                transaction.getAmount()
        );
    }
}
