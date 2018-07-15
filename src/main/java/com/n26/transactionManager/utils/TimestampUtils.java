package com.n26.transactionManager.utils;

public class TimestampUtils {
    public static final int MILLISECOND = 1000;
    public static final int SECOND = 60;

    public static boolean isValidTimestamp(long currentTimestamp, long tranTimestamp) {
        return ((currentTimestamp / MILLISECOND) - (tranTimestamp / MILLISECOND)) <= SECOND;
    }

    public static int findBucket(long timestamp) {
        return (int) (timestamp / MILLISECOND) % SECOND;
    }
}
