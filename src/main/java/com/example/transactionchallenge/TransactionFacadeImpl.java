package com.example.transactionchallenge;

import com.example.transactionchallenge.controller.dto.AccountRequest;
import com.example.transactionchallenge.controller.dto.AccountResponse;
import com.example.transactionchallenge.controller.dto.TransactionRequest;
import com.example.transactionchallenge.controller.dto.TransactionResponse;
import com.example.transactionchallenge.domain.DomainConverter;
import com.example.transactionchallenge.domain.repository.AccountRepository;
import com.example.transactionchallenge.domain.repository.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class TransactionFacadeImpl implements TransactionFacade {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final DomainConverter converter;

    public TransactionFacadeImpl(TransactionRepository transactionRepository, AccountRepository accountRepository, DomainConverter converter) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.converter = converter;
    }

    @Override
    public AccountResponse getAccount(Long id) {
        var account = accountRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return converter.toResponse(account);
    }

    @Override
    public AccountResponse createAccount(AccountRequest accountRequest) {

        if (accountRequest.documentNumber() == null ||
            accountRequest.documentNumber().trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Document number is required and cannot be empty");
        }

        var account = converter.toEntity(accountRequest);
        var saved =
                accountRepository.findByDocumentNumber(account.getDocumentNumber())
                .orElse(accountRepository.save(account));
        return converter.toResponse(saved);
    }

    @Override
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {

        if (transactionRequest.accountId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Account ID is required and cannot be empty");
        }

        var account =
                accountRepository.findById(transactionRequest.accountId())
                        .orElseThrow(() ->
                                new  ResponseStatusException(HttpStatus.NOT_FOUND));
        var transaction = converter.toEntity(transactionRequest, account);
        var saved = transactionRepository.save(transaction);

        return converter.toResponse(saved);
    }
}
