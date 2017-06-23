package com.avrios.sample.exchange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.avrios.sample.exchange.service.ExchangeService;

@Component
public class ScheduledTasks {

    @Autowired
    ExchangeService exchangeService;

    @Scheduled(fixedRate = 60 * 1000 * 30)
    public void periodicallyFetchExchangeRates() {
        new Thread(() -> exchangeService.fetchExchangeRates()).run();//on new thread to not block scheduler thread.
    }
}