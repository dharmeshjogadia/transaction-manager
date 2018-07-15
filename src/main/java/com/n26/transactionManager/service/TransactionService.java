package com.n26.transactionManager.service;

import com.n26.transactionManager.dao.TransactionDao;
import com.n26.transactionManager.exception.InvalidTransactionException;
import com.n26.transactionManager.pojo.AddTransaction;
import com.n26.transactionManager.pojo.TransactionStatistic;
import com.n26.transactionManager.utils.TimestampUtils;

public class TransactionService {

    private final TransactionDao dao;

    public TransactionService() {
        this.dao = new TransactionDao();
    }

    /**
     * Valid and add transaction
     * @param transaction
     * @throws InvalidTransactionException if transaction fails validation
     */
    public void add(AddTransaction transaction) throws InvalidTransactionException {
        if (TimestampUtils.isValidTimestamp(System.currentTimeMillis(), transaction.getTimestamp())) {
            dao.add(transaction);
        } else {
            throw new InvalidTransactionException("Timestamp validation failed");
        }
    }

    /**
     * @return {@code TransactionStatistic} from last 60 seconds transactions
     */
    public TransactionStatistic statistic() {
        return dao.summery();
    }
}
