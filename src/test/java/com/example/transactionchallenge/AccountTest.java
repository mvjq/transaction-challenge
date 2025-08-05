package com.example.transactionchallenge;

import com.example.transactionchallenge.controller.dto.AccountRequest;
import com.example.transactionchallenge.domain.entity.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AccountTest {
    @Test
    void createAccountWithNullableDocumentNumberAndThrowsException() {
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> new Account(null));

        assertEquals("Document number cannot be null or empty",
                exception.getMessage());
    }
}
