package com.avrios.sample.exchange.repository;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.avrios.sample.exchange.model.DayExchangeRates;
import com.avrios.sample.exchange.model.ExchangeRate;

@Repository
public class ExchangeRepository {

    private DayExchangeRates dayExchangeRates;
    private Date latestDate;

    /**
     * Returns exchange rate for specified date. If date is null, returns latest available exchange rate.
     */
    public ExchangeRate getExchangeRateOnDay(String currency, Date date) {
        /*TODO: consider exception if data were not updated for certain time especially when querying 'today'.
        */
        date = Optional.ofNullable(date).orElse(latestDate);
        currency = currency.toUpperCase();
        Map<String, Double> rates = dayExchangeRates.get(date);
        if (rates == null) {
            throw new ExchangeRateNotFound("No data available for specified date.");
        }
        Double rate = rates.get(currency);
        if (rate == null) {
            throw new ExchangeRateNotFound("No exchange rate for specified currency.");
        }
        return new ExchangeRate(date, currency, rate);
    }


    public void setDayExchangeRates(DayExchangeRates dayExchangeRates) {
        this.dayExchangeRates = dayExchangeRates;
        this.latestDate = dayExchangeRates.keySet().stream().max(Date::compareTo).orElse(null);
    }

    public static class ExchangeRateNotFound extends RuntimeException {
        public ExchangeRateNotFound(String message) {
            super(message);
        }

    }
}
