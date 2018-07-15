package com.n26.transactionManager.utils;

import org.junit.Assert;
import org.junit.Test;

public class TimestampUtilsTest {

    @Test
    public void isValidTimestamp() throws Exception {
        Assert.assertTrue(TimestampUtils.isValidTimestamp(System.currentTimeMillis(),
                System.currentTimeMillis() - TimestampUtils.MILLISECOND));
        Assert.assertFalse(TimestampUtils.isValidTimestamp(System.currentTimeMillis(),
                System.currentTimeMillis() - ((TimestampUtils.SECOND + 1) * TimestampUtils.MILLISECOND)));
    }

    @Test
    public void findBucket() throws Exception {
        int bucket = TimestampUtils.findBucket(61000);
        Assert.assertEquals(bucket, 1);
        bucket = TimestampUtils.findBucket(45000);
        Assert.assertEquals(bucket, 45);
    }
}