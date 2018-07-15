package com.n26.transactionManager.service;

import com.n26.transactionManager.exception.InvalidTransactionException;
import com.n26.transactionManager.pojo.AddTransaction;
import com.n26.transactionManager.pojo.TransactionStatistic;
import com.n26.transactionManager.utils.TimestampUtils;
import org.junit.Assert;
import org.junit.Test;

public class TransactionServiceTest {

    TransactionService service;

    @Test
    public void add() throws Exception {
        service = new TransactionService();
        AddTransaction transaction = new AddTransaction(1200, System.currentTimeMillis());
        service.add(transaction);
        TransactionStatistic statistic = service.statistic();
        Assert.assertEquals(statistic.getSum(), 1200, 0);
        Assert.assertEquals(statistic.getAvg(), 1200, 0);
        Assert.assertEquals(statistic.getMin(), 1200, 0);
        Assert.assertEquals(statistic.getMax(), 1200, 0);
        Assert.assertEquals(statistic.getCount(), 1);
    }

    @Test(expected = InvalidTransactionException.class)
    public void addInvalidTransaction() throws Exception {
        service = new TransactionService();
        long tranTimestamp = System.currentTimeMillis() - ((TimestampUtils.SECOND + 1) * TimestampUtils.MILLISECOND);
        AddTransaction transaction = new AddTransaction(1200, tranTimestamp);
        service.add(transaction);
    }

    @Test
    public void statistic() throws Exception {}

}