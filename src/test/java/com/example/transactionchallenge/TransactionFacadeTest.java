package com.example.transactionchallenge;


import com.example.transactionchallenge.controller.dto.AccountRequest;
import com.example.transactionchallenge.controller.dto.AccountResponse;
import com.example.transactionchallenge.controller.dto.TransactionRequest;
import com.example.transactionchallenge.controller.dto.TransactionResponse;
import com.example.transactionchallenge.domain.DomainConverter;
import com.example.transactionchallenge.domain.OperationType;
import com.example.transactionchallenge.domain.entity.Account;
import com.example.transactionchallenge.domain.entity.Transaction;
import com.example.transactionchallenge.domain.repository.AccountRepository;
import com.example.transactionchallenge.domain.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionFacadeTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private DomainConverter domainConverter;

    @InjectMocks
    private TransactionFacadeImpl facade;

    private Account account;
    private AccountRequest accountRequest;
    private AccountResponse accountResponse;
    private Transaction transaction;
    private TransactionResponse transactionResponse;

    @BeforeEach
    public void setUp() {
        account = new Account(1L, "39293399865");
        transaction = new Transaction(account,
                OperationType.CREDIT_VOUCHER.getValue(), 100.00);
        transactionResponse = new TransactionResponse(1L , OperationType.CREDIT_VOUCHER.getValue(), 100.00);
        accountRequest = new AccountRequest("39293399865");
        accountResponse = new AccountResponse(1L, "39293399865");

    }

    //
    // good path
    //

    @Test
    void createAccountSucceed() {
        when(domainConverter.toEntity(accountRequest)).thenReturn(account);
        when(domainConverter.toResponse(account)).thenReturn(accountResponse);
        when(accountRepository.save(account)).thenReturn(account);

        var result = facade.createAccount(accountRequest);

        assertThat(result).isNotNull();
        assertThat(result.documentNumber()).isEqualTo("39293399865");

        verify(domainConverter, times(1)).toEntity(accountRequest);
        verify(accountRepository, times(1)).save(account);
        verify(domainConverter, times(1)).toResponse(account);
    }

    @Test
    void getAccountSucceed() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(domainConverter.toResponse(account)).thenReturn(accountResponse);

        var result = facade.getAccount(1L);

        assertNotNull(result);
    }

    @Test
    void createTransactionSucceed() {
        var request = new TransactionRequest(1L, OperationType.CREDIT_VOUCHER.getValue(), 100.00);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(domainConverter.toEntity(request, account)).thenReturn(transaction);
        when(transactionRepository.save(transaction)).thenReturn(transaction);
        when(domainConverter.toResponse(transaction)).thenReturn(transactionResponse);


        var response = facade.createTransaction(request);

        assertNotNull(response);
    }


    //
    // bad path
    //


    // account with documentNUmber == null
    @Test
    void createAccountWIthDocumentNumberEqualNullError() {
        var request = new AccountRequest(null);
        var exception =
                assertThrows(ResponseStatusException.class,
                        () -> facade.createAccount(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    // account not found
    @Test
    void getNonExistentAccountNotFound() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> facade.getAccount(100L));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    // transaction without account
    @Test
    void createTransactionWithNonExistsAccountError() {
        var request = new TransactionRequest(100L, 1, 100.00);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> facade.createTransaction(request));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    // transaction with wrong operation type
    @Test
    void createTransactionWithWrongOperationTypeError() {
        var request = new TransactionRequest(1L, -1, 100.00);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        doThrow(IllegalStateException.class).when(domainConverter).toEntity(request, account);

        assertThrows(IllegalStateException.class,
                () -> facade.createTransaction(request));
    }


    @Test
    // transaction with negative ammount
    void createTransactionWithNegativeAmountError() {
        var request = new TransactionRequest(1L, 1, -100.00);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        doThrow(IllegalStateException.class).when(domainConverter).toEntity(request, account);

        assertThrows(IllegalStateException.class,
                () -> facade.createTransaction(request));
    }
}


