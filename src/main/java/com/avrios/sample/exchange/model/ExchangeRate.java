package com.avrios.sample.exchange.model;

import java.util.Date;

public class ExchangeRate {
    Date date;
    String currency;
    double rate;

    public ExchangeRate(Date date, String currency, double rate) {
        this.date = date;
        this.currency = currency;
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public String getCurrency() {
        return currency;
    }

    public double getRate() {
        return rate;
    }
}
