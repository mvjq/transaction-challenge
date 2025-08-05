package com.example.transactionchallenge.domain;

public enum OperationType {
    NORMAL_PURCHASE(1, true),
    PURCHASE_INSTALLMENTS(2, true),
    WITHDRAWAL(3, true),
    CREDIT_VOUCHER(4, false);

    private final int value;
    private final boolean isNegativeAmount;

    OperationType(int value, boolean isNegativeAmount) {
        this.value = value;
        this.isNegativeAmount = isNegativeAmount;
    }

    public int getValue() {
        return value;
    }

    public static OperationType fromValue(int value) {
        for (OperationType operationType : OperationType.values()) {
            if (operationType.getValue() == value) {
                return operationType;
            }
        }
        throw new IllegalStateException("No OperationType with value " + value + " found");
    }

    public boolean isNegativeAmount() {
        return isNegativeAmount;
    }
}
