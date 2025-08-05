package com.example.transactionchallenge.domain.entity;

import jakarta.persistence.*;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;
    @Column(unique = true, name = "document_number")
    private String documentNumber;

    public Account(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Account(Long id, String documentNumber) {
        this.id = id;
        this.documentNumber = documentNumber;
    }

    public Account() {}

    public Long getId() {
        return id;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }
}
