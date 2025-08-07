package com.example.transactionchallenge.service;

import com.example.transactionchallenge.controller.dto.TransactionRequest;
import com.example.transactionchallenge.domain.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public interface TransactionService {
    Transaction createTransaction(TransactionRequest transactionRequest);
    Transaction getTransaction(Long id);
}
