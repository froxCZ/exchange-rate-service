package com.avrios.sample.exchange.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private boolean fetching = false;

    @Value("${ecbService.retryTimeout}")
    private int ecbServiceRetryTimeout;

    /**
     * Fetch exchange rates until succeeded.
     */
    public void fetchExchangeRates() {
        if (fetching) {//loop is already running
            return;
        }
        fetching = true;
        while (true) {
            try {
                fetchExchangeRatesOnce();
                log.info("Fetched latest exchange rates.");
                fetching = false;
                return;
            } catch (Throwable e) {
                log.error("Failed to get latest exchange rates.", e);
            }
            try {
                Thread.sleep(ecbServiceRetryTimeout);
            } catch (InterruptedException ignored) {

            }
        }
    }

    private void fetchExchangeRatesOnce() throws Exception {
        DayExchangeRates exchangeRates = ecbExternalService.fetchExchangeRates();
        exchangeRepository.setDayExchangeRates(exchangeRates);
    }

    public ExchangeRate getExchangeRate(String currency, Date date) {
        return exchangeRepository.getExchangeRateOnDay(currency, date);
    }


}
