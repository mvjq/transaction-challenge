package com.example.transactionchallenge;


import com.example.transactionchallenge.controller.dto.AccountRequest;
import com.example.transactionchallenge.controller.dto.AccountResponse;
import com.example.transactionchallenge.controller.dto.TransactionRequest;
import com.example.transactionchallenge.controller.dto.TransactionResponse;

public interface TransactionFacade {
    AccountResponse createAccount(AccountRequest accountRequest);
    AccountResponse getAccount(Long id);
    TransactionResponse createTransaction(TransactionRequest transactionRequest);
}
