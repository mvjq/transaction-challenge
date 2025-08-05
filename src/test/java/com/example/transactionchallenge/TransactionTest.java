package com.example.transactionchallenge;

import com.example.transactionchallenge.domain.OperationType;
import com.example.transactionchallenge.domain.entity.Account;
import com.example.transactionchallenge.domain.entity.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TransactionTest {

    @Test
    void createTransactionWithNegativeAmountThrows() {
        var account = new Account(1L, "39293399865");
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> new Transaction(account,
                        OperationType.CREDIT_VOUCHER.getValue(), -100));

        assertEquals("Amount cant be zero or negative", exception.getMessage());
    }

    @Test
    void createTransactionWithWrongOperationTypeThrows() {
        var account = new Account(1L, "39293399865");
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> new Transaction(account, -1, 100));

        assertEquals("No OperationType with value -1 found",
                exception.getMessage());
    }

}
