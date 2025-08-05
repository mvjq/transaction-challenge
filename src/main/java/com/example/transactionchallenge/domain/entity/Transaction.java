package com.example.transactionchallenge.domain.entity;

import com.example.transactionchallenge.domain.OperationType;
import jakarta.persistence.*;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long accountId;
    private OperationType operationType;
    private double amount;

    public Transaction() {}

    public Transaction(Long accountId, int operationId,
                       double amount) {
        // validation
        var operationType =  OperationType.fromValue(operationId);
        if (!validAmount(amount)) {
            throw new IllegalStateException("Amount cant be zero or negative");
        }

        this.accountId = accountId;
        this.operationType = operationType;
        this.amount = amountWithOperationType(amount);
    }

    private double amountWithOperationType(double amount) {
        if (operationType.isNegativeAmount()) {
            return -1 * amount;
        }
        return amount;
    }

    private boolean validAmount(double amount) {
        return amount > 0;
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
