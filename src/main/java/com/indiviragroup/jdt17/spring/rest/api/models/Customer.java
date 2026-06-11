package com.indiviragroup.jdt17.spring.rest.api.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mst_customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID customerId;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;
    private String phone;
    private String address;

    @OneToMany(mappedBy = "customer")
    private List<Account> accounts;
}
