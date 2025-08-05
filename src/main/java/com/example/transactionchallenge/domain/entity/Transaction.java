package com.example.transactionchallenge.domain.entity;

import com.example.transactionchallenge.domain.OperationType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "account_id",
            referencedColumnName = "account_id",
            insertable = false,
            updatable = false
    )
    private Account account;
    @Column(name = "operation_type_id")
    private OperationType operationType;
    private double amount;
    private LocalDateTime transactionDate;

    public Transaction() {}

    public Transaction(Account account, int operationId,
                       double amount) {
        // validation
        var operationType =  OperationType.fromValue(operationId);
        if (!validAmount(amount)) {
            throw new IllegalStateException("Amount cant be zero or negative");
        }

        this.account = account;
        this.operationType = operationType;
        this.amount = amountWithOperationType(amount);
        this.transactionDate = LocalDateTime.now();
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

    public Account getAccount() {
        return account;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
}
