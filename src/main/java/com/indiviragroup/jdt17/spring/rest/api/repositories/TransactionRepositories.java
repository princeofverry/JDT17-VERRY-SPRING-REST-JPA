package com.indiviragroup.jdt17.spring.rest.api.repositories;

import com.indiviragroup.jdt17.spring.rest.api.enums.TransactionType;
import com.indiviragroup.jdt17.spring.rest.api.models.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepositories extends JpaRepository<Transaction, UUID> {

    @org.springframework.data.jpa.repository.Query("SELECT t FROM Transaction t WHERE t.sourceAccount.accountId = :accountId OR t.destinationAccount.accountId = :accountId")
    Page<Transaction> findByAccount(
            @org.springframework.data.repository.query.Param("accountId") UUID accountId,
            Pageable pageable
    );

    @org.springframework.data.jpa.repository.Query("SELECT t FROM Transaction t WHERE (t.sourceAccount.accountId = :accountId OR t.destinationAccount.accountId = :accountId) AND t.transactionType = :type")
    Page<Transaction> findByAccountAndType(
            @org.springframework.data.repository.query.Param("accountId") UUID accountId,
            @org.springframework.data.repository.query.Param("type") TransactionType type,
            Pageable pageable
    );
}
