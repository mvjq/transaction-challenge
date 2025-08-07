package com.example.transactionchallenge.domain.entity.v2;

import jakarta.persistence.*;

@Entity
public class TransactionV2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AccountV2 account;
    private Double amount;
    private String description;

    @Deprecated
    public TransactionV2(){}

    public TransactionV2(AccountV2 account, Double amount, String description) {
        this.account = account;
        this.amount = amount;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public AccountV2 getAccount() {
        return account;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", account=" + account +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}
