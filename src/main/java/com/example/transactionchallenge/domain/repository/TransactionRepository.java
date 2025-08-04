package com.example.transactionchallenge.domain.repository;

import com.example.transactionchallenge.domain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {}
