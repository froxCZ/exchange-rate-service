package com.avrios.sample.exchange.repository;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.avrios.sample.exchange.model.DayExchangeRates;
import com.avrios.sample.exchange.model.ExchangeRate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
public class ExchangeRepositoryTest {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    public static final String CURRENCY1 = "CURR1";
    public static final String CURRENCY2 = "CURR2";

    private ExchangeRepository fixture = new ExchangeRepository();


    @Test
    public void getExchangeRateOnDay() throws Exception {
        DayExchangeRates dayExchangeRates = new DayExchangeRates();
        String[] currencies = new String[] {CURRENCY1, CURRENCY2};
        dayExchangeRates.put(formatter.parse("2017-06-18"), createDayRatesMap(currencies, new double[] {30.1, 48.4}));
        dayExchangeRates.put(formatter.parse("2017-06-19"), createDayRatesMap(currencies, new double[] {33.8, 45.3}));
        dayExchangeRates.put(formatter.parse("2017-06-20"), createDayRatesMap(currencies, new double[] {32.3, 41.0}));

        fixture.setDayExchangeRates(dayExchangeRates);

        ExchangeRate latest = fixture.getExchangeRateOnDay(CURRENCY1, null);
        ExchangeRate expected = new ExchangeRate(formatter.parse("2017-06-20"), CURRENCY1, 32.3);
        assertThat(latest, is(expected));
        assertThat(fixture.getExchangeRateOnDay(CURRENCY1, formatter.parse("2017-06-20")), is(expected));

        expected = new ExchangeRate(formatter.parse("2017-06-19"), CURRENCY2, 45.3);
        assertThat(fixture.getExchangeRateOnDay(CURRENCY2, formatter.parse("2017-06-19")), is(expected));

        expected = new ExchangeRate(formatter.parse("2017-06-18"), CURRENCY2, 48.4);
        assertThat(fixture.getExchangeRateOnDay(CURRENCY2, formatter.parse("2017-06-18")), is(expected));


    }


    private HashMap<String, Double> createDayRatesMap(String[] currencies, double[] rates) {
        HashMap<String, Double> ratesMap = new HashMap<>();
        for (int i = 0; i < currencies.length; i++) {
            ratesMap.put(currencies[i], rates[i]);
        }
        return ratesMap;

    }

}