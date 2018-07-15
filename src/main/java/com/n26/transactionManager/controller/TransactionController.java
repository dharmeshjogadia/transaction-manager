package com.n26.transactionManager.controller;

import com.n26.transactionManager.exception.InvalidTransactionException;
import com.n26.transactionManager.pojo.AddTransaction;
import com.n26.transactionManager.pojo.TransactionStatistic;
import com.n26.transactionManager.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    private final TransactionService service;

    public TransactionController() {
        service = new TransactionService();
    }

    @RequestMapping(value = "transactions",
                    method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addTransaction(@RequestBody AddTransaction transaction) {
        try {
            service.add(transaction);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (InvalidTransactionException e) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }


    @RequestMapping(value = "statistics",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionStatistic> statistics() {
        return new ResponseEntity<>(service.statistic(), HttpStatus.OK);
    }
}
