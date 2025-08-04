package com.example.transactionchallenge.domain;

public enum OperationType {
    NORMAL_PURCHASE(1),
    PURCHASE_INSTALLMENTS(2),
    WITHDRAWAL(3),
    CREDIT_VOUCHER(4);

    private final int value;
    OperationType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
