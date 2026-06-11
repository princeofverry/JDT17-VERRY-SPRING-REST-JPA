package com.indiviragroup.jdt17.spring.rest.api.repositories;

import com.indiviragroup.jdt17.spring.rest.api.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepositories extends JpaRepository<Account, String > {

    Optional<Account> findByAccountNumber(String accountNumber);

    boolean existsByAccountNumber(String accountNumber);
}
