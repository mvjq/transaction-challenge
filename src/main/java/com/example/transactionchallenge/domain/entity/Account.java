package com.example.transactionchallenge.domain.entity;

import jakarta.persistence.*;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String documentNumber;

    public Account(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Account() {}

    public Long getId() {
        return id;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }
}
