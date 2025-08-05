package com.example.transactionchallenge.domain;

import com.example.transactionchallenge.controller.dto.AccountRequest;
import com.example.transactionchallenge.controller.dto.AccountResponse;
import com.example.transactionchallenge.controller.dto.TransactionRequest;
import com.example.transactionchallenge.controller.dto.TransactionResponse;
import com.example.transactionchallenge.domain.entity.Account;
import com.example.transactionchallenge.domain.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class DomainConverter {

    public Account toAccount(AccountRequest accountRequest) {
        return new Account(accountRequest.documentNumber());
    }

    public AccountResponse toResponse(Account account) {
        return new AccountResponse(account.getId(), account.getDocumentNumber());
    }

    public Transaction toTransaction(TransactionRequest transactionRequest,
                                     Account account) {
        return new Transaction(account.getId(),
                transactionRequest.operationTypeId(),
                transactionRequest.amount());
    }

    public TransactionResponse toResponse(Transaction transaction) {
        return null;
    }
}
