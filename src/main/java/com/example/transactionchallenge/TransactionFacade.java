package com.example.transactionchallenge;


import com.example.transactionchallenge.controller.dto.AccountRequest;
import com.example.transactionchallenge.controller.dto.AccountResponse;
import com.example.transactionchallenge.controller.dto.TransactionRequest;

public interface TransactionFacade {
    void createAccount(AccountRequest accountRequest);
    AccountResponse getAccount(Long id);
    void createTransaction(TransactionRequest transactionRequest);
}
