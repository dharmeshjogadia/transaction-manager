package com.n26.transactionManager.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class TransactionStatistic {

    private double sum;
    private double avg;
    private double min = Double.MAX_VALUE;
    private double max = Double.MIN_VALUE;
    private int count;
    @JsonIgnore
    private long timestamp;

    public TransactionStatistic() {}

    public TransactionStatistic(AddTransaction transaction) {
        this.sum = transaction.getAmount();
        this.min = Math.min(this.min, transaction.getAmount());
        this.max = Math.max(this.max, transaction.getAmount());
        this.count = 1;
        this.timestamp = transaction.getTimestamp();
    }

    public void merge(TransactionStatistic newTran) {
        this.sum += newTran.sum;
        this.count += newTran.count;
        this.min = Math.min(this.min, newTran.min);
        this.max = Math.max(this.max, newTran.max);
        this.avg = this.sum / (double) this.count;
    }
}
