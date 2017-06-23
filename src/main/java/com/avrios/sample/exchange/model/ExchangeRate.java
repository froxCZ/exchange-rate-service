package com.avrios.sample.exchange.model;

import java.util.Date;
import java.util.Objects;

public class ExchangeRate {
    private Date date;
    private String currency;
    private double rate;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRate that = (ExchangeRate) o;
        return Double.compare(that.getRate(), getRate()) == 0 &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getCurrency(), that.getCurrency());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getCurrency(), getRate());
    }
}
