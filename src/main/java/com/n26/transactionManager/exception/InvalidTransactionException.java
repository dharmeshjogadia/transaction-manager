package com.n26.transactionManager.exception;

public class InvalidTransactionException extends Exception {

    public InvalidTransactionException(String msg) {
        super(msg);
    }
}
