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
        validateDocumentNumber(documentNumber);

        this.documentNumber = documentNumber;
    }

    public Account(Long id, String documentNumber) {
        validateDocumentNumber(documentNumber);

        this.id = id;
        this.documentNumber = documentNumber;
    }

    private static void validateDocumentNumber(String documentNumber) {
        if (documentNumber == null || documentNumber.trim().isEmpty()) {
            throw new IllegalStateException("Document number cannot be null or empty");
        }
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
