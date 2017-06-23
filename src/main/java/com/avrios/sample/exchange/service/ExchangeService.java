package com.avrios.sample.exchange.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avrios.sample.exchange.model.DayExchangeRates;
import com.avrios.sample.exchange.model.ExchangeRate;
import com.avrios.sample.exchange.repository.ExchangeRepository;

@Service
public class ExchangeService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EcbExternalService ecbExternalService;

    @Autowired
    ExchangeRepository exchangeRepository;


    public void fetchExchangeRates() {
        DayExchangeRates exchangeRates = null;
        try {
            exchangeRates = ecbExternalService.fetchExchangeRates();
            exchangeRepository.setDayExchangeRates(exchangeRates);
        } catch (Throwable e) {
            //TODO: do few attempts
            log.error("Failed to get latest exchange rates.", e);
        }
    }

    public ExchangeRate getExchangeRate(String currency, Date date) {
        return exchangeRepository.getExchangeRateOnDay(currency, date);
    }


}
