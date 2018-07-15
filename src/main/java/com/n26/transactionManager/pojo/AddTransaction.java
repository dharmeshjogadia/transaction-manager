package com.n26.transactionManager.pojo;

import lombok.Data;

@Data
public class AddTransaction {
    private double amount;
    private long timestamp;

    public AddTransaction() {}

    public AddTransaction(double amount, long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }
}
