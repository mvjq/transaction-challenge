package com.example.transactionchallenge;

import com.example.transactionchallenge.controller.dto.AccountRequest;
import com.example.transactionchallenge.controller.dto.AccountResponse;
import com.example.transactionchallenge.controller.dto.TransactionRequest;
import org.springframework.stereotype.Component;

@Component
public class TransactionFacadeImpl implements TransactionFacade {



    @Override
    public void createAccount(AccountRequest accountRequest) {

    }
    @Override
    public AccountResponse getAccount(Long id) {
        return null;
    }

    @Override
    public void createTransaction(TransactionRequest transactionRequest) {}
}
