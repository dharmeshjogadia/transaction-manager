package com.n26.transactionManager.dao;

import com.n26.transactionManager.pojo.AddTransaction;
import com.n26.transactionManager.pojo.TransactionStatistic;
import com.n26.transactionManager.utils.TimestampUtils;

public class TransactionDao {
    private final Object LOCK;
    private TransactionStatistic[] transactionState;

    public TransactionDao() {
        transactionState = new TransactionStatistic[TimestampUtils.SECOND];
        LOCK = new Object();
        for (int i = 0; i < transactionState.length; i++) {
            transactionState[i] = new TransactionStatistic();
        }
    }

    public void add(AddTransaction transaction) {
        int bucket = TimestampUtils.findBucket(transaction.getTimestamp());

        TransactionStatistic currentTransaction = new TransactionStatistic(transaction);
        synchronized (transactionState[bucket]) {
            if (TimestampUtils.isValidTimestamp(transaction.getTimestamp(), transactionState[bucket].getTimestamp())) {
                transactionState[bucket].merge(currentTransaction);
            } else {
                transactionState[bucket] = currentTransaction;
            }
        }
    }

    public TransactionStatistic summery() {
        TransactionStatistic finalSummery = new TransactionStatistic();
        long currentTimestamp = System.currentTimeMillis();
        synchronized (LOCK) {
            for (int i = 0; i < transactionState.length; i++) {
                if (TimestampUtils.isValidTimestamp(currentTimestamp, transactionState[i].getTimestamp())) {
                    finalSummery.merge(transactionState[i]);
                } else {
                    transactionState[i] = new TransactionStatistic();
                }
            }
        }
        return finalSummery;
    }
}
