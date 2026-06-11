package com.indiviragroup.jdt17.spring.rest.api.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.crypto.Mac;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "mst_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @Column(name = "account_id", length = 8)
    private String accountId;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "sourceAccount")
    private List<Transaction> outgoingTransactions;

    @OneToMany(mappedBy = "destinationAccount")
    private List<Transaction> incomingTransactions;
}
