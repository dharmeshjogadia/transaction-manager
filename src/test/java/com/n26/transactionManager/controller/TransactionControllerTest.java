package com.n26.transactionManager.controller;

import com.n26.transactionManager.pojo.AddTransaction;
import com.n26.transactionManager.pojo.TransactionStatistic;
import com.n26.transactionManager.utils.TimestampUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerTest {

    URL base;
    @LocalServerPort
    private int port;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
    }

    @Test
    public void addTransaction() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        AddTransaction transaction = new AddTransaction(1200, System.currentTimeMillis());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<AddTransaction> entity = new HttpEntity<>(transaction, headers);
        ResponseEntity responseEntity = restTemplate.exchange(base.toString() + "/transactions", HttpMethod.POST,
                entity, String.class);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void addInvalidTransaction() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        long tranTimestamp = System.currentTimeMillis() - ((TimestampUtils.SECOND + 1) * TimestampUtils.MILLISECOND);
        AddTransaction transaction = new AddTransaction(1200, tranTimestamp);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<AddTransaction> entity = new HttpEntity<>(transaction, headers);
        ResponseEntity responseEntity = restTemplate.exchange(base.toString() + "/transactions", HttpMethod.POST,
                entity, String.class);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void statistics() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TransactionStatistic> responseEntity = restTemplate.getForEntity(base.toString() + "/statistics",
                TransactionStatistic.class);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Assert.assertNotNull(responseEntity.getBody());
    }
}