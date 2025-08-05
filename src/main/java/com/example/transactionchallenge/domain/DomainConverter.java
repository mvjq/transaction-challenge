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

    public Account toEntity(AccountRequest accountRequest) {
        return new Account(accountRequest.documentNumber());
    }

    public AccountResponse toResponse(Account account) {
        return new AccountResponse(account.getId(), account.getDocumentNumber());
    }

    public AccountRequest toRequest(Account account) {
        return new AccountRequest(account.getDocumentNumber());
    }

    public Transaction toEntity(TransactionRequest transactionRequest,
                                Account account) {
        return new Transaction(account,
                transactionRequest.operationTypeId(),
                transactionRequest.amount());
    }

    public TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(transaction.getAccount().getId(),
                transaction.getOperationType().getValue(),
                transaction.getAmount());
    }

    public TransactionRequest toRequest(Transaction transaction) {
        return new TransactionRequest(transaction.getAccount().getId(),
                transaction.getOperationType().getValue(),
                transaction.getAmount());
    }
}
