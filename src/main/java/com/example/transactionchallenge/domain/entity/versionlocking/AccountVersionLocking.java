package com.example.transactionchallenge.domain.entity.versionlocking;

import jakarta.persistence.*;

@Entity
@Table(name = "account_versionlocking")
public class AccountVersionLocking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String holderName;

    @Column(nullable = false, precision = 9)
    private Double balance;

    // version is used for the optimistic version locking
    @Version
    private long version;

    @Deprecated
    public AccountVersionLocking(){}

    public AccountVersionLocking(String holderName, Double balance) {
        this.holderName = holderName;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public String getHolderName() {
        return holderName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "OptAccount{" +
                "id=" + id +
                ", holderName='" + holderName + '\'' +
                ", balance=" + balance +
                '}';
    }
}