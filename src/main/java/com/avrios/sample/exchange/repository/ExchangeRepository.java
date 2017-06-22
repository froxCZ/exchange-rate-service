package com.avrios.sample.exchange.repository;

import java.util.Date;

import com.avrios.sample.exchange.model.DayExchangeRates;

public class ExchangeRepository {

    private DayExchangeRates dayExchangeRates;

    public Double getExchangeRate(String currency) {
        return 0.0;
    }

    public Double getExchangeRateOnDay(String currency, Date date) {
        return 0.0;
    }


    public void setDayExchangeRates(DayExchangeRates dayExchangeRates) {
        this.dayExchangeRates = dayExchangeRates;
    }
}
