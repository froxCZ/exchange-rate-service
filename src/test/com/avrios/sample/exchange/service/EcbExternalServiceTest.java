package com.avrios.sample.exchange.service;

import org.junit.Test;

import com.avrios.sample.exchange.model.DayExchangeRates;

public class EcbExternalServiceTest {
    @Test
    public void fetchExchangeRates() throws Exception {
        EcbExternalService ecbService = new EcbExternalService();
        DayExchangeRates x = ecbService.fetchExchangeRates();
        System.out.println(x);
    }

}