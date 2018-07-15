package com.n26.transactionManager.dao;

import com.n26.transactionManager.pojo.AddTransaction;
import com.n26.transactionManager.pojo.TransactionStatistic;
import com.n26.transactionManager.utils.TimestampUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TransactionDaoTest {

    private TransactionDao dao;

    @Before
    public void setup() {}

    @Test
    public void add() throws Exception {
        dao = new TransactionDao();
        long currentTimestamp = System.currentTimeMillis();
        long sum = 0;
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (int i = 1; i <= 75; i++) {
            double amount = 100D;
            sum += amount;
            min = Math.min(min, amount);
            max = Math.max(max, amount);

            AddTransaction transaction = new AddTransaction(amount, currentTimestamp);
            dao.add(transaction);
            currentTimestamp = currentTimestamp - TimestampUtils.MILLISECOND;
            TransactionStatistic statistic = dao.summery();
            Assert.assertEquals(statistic.getSum(), sum, 0 );
            Assert.assertEquals(statistic.getAvg(), amount, 0 );
            Assert.assertEquals(statistic.getMin(),min, 0 );
            Assert.assertEquals(statistic.getMax(), max, 0);
            Assert.assertEquals(statistic.getCount(), i);
        }
    }
}