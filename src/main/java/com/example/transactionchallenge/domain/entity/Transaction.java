package com.example.transactionchallenge.domain.entity;

import com.example.transactionchallenge.domain.OperationType;
import jakarta.persistence.*;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long accountId;
    private OperationType operationType;
    private double amount;

    public Transaction() {}

    public Transaction(Long accountId, OperationType operationType, double amount) {
        this.accountId = accountId;
        this.operationType = operationType;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public double getAmount() {
        return amount;
    }
}
