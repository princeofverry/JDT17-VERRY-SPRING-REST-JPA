package com.indiviragroup.jdt17.spring.rest.api.repositories;

import com.indiviragroup.jdt17.spring.rest.api.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/*
    Karena extend JpaRepository dapet:
    save()
    findAll()
    findById()
    deleteById()
 */
public interface CustomerRepositories extends JpaRepository<Customer, UUID> {
    boolean existsByEmail(String email);
    boolean existsById(UUID id);
}
