package com.indiviragroup.jdt17.spring.rest.api.services;

import com.indiviragroup.jdt17.spring.rest.api.dto.CustomerRequest;
import com.indiviragroup.jdt17.spring.rest.api.dto.CustomerResponse;
import com.indiviragroup.jdt17.spring.rest.api.models.Customer;
import com.indiviragroup.jdt17.spring.rest.api.repositories.CustomerRepositories;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepositories repository;

    /*
        pake JPA di service-nya jadi nnti tinggal panggil ORM
     */


    public CustomerResponse create(CustomerRequest request) {

        if (repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Customer customer = new Customer();

        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());

        customer = repository.save(customer);

        return mapToResponse(customer);
    }

    private CustomerResponse mapToResponse(
            Customer customer
    ) {
        return new CustomerResponse(
                customer.getCustomerId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddress()
        );
    }

    public List<CustomerResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public CustomerResponse getById(UUID id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() ->
                new RuntimeException("Customer not found"));

        return mapToResponse(customer);
    }


    public CustomerResponse update(
            UUID id,
            CustomerRequest request
    ) {

        Customer customer = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());

        customer = repository.save(customer);

        return mapToResponse(customer);
    }

    public void delete(UUID id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        repository.delete(customer);
    }
}
