package com.avrios.sample.exchange.repository;

import java.util.Date;
import java.util.Map;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Repository;

import com.avrios.sample.exchange.model.DayExchangeRates;
import com.avrios.sample.exchange.model.ExchangeRate;

@Repository
public class ExchangeRepository {

    private DayExchangeRates dayExchangeRates;
    /**
     * Latest date available.
     */
    private Date latestDate;

    /**
     * Returns exchange rate for specified date. If date is null, returns latest available exchange rate.
     */
    @NotNull
    public ExchangeRate getExchangeRateOnDay(@NotNull String currency, @Nullable Date date) {
        //TODO: consider exception if data were not updated for certain time especially when date is null (=today).
        if (dayExchangeRates == null) {
            throw new ExchangeRateNotFound("Exchange rates are not available yet. Try again later.");
        }
        date = date != null ? date : latestDate;
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


    public void setDayExchangeRates(@NotNull DayExchangeRates dayExchangeRates) {
        this.dayExchangeRates = dayExchangeRates;
        this.latestDate = dayExchangeRates.keySet().stream().max(Date::compareTo).orElse(null);
    }

    public static class ExchangeRateNotFound extends RuntimeException {
        public ExchangeRateNotFound(String message) {
            super(message);
        }

    }
}
