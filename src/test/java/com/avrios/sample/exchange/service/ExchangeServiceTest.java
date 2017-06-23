package com.avrios.sample.exchange.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.avrios.sample.exchange.model.DayExchangeRates;
import com.avrios.sample.exchange.repository.ExchangeRepository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeServiceTest {

    @Mock
    private EcbExternalService ecbExternalService;

    @Mock
    private ExchangeRepository exchangeRepository;

    @InjectMocks
    private ExchangeService fixture;

    @Test
    public void fetchExchangeRates() throws Exception {
        DayExchangeRates dayExchangeRates = new DayExchangeRates();
        when(ecbExternalService.fetchExchangeRates()).thenReturn(dayExchangeRates);

        fixture.fetchExchangeRates();

        ArgumentCaptor<DayExchangeRates> exchangeRatesCaptor = ArgumentCaptor.forClass(DayExchangeRates.class);
        verify(exchangeRepository, times(1)).setDayExchangeRates(exchangeRatesCaptor.capture());

        assertThat("Exchange rates are different", exchangeRatesCaptor.getValue() == dayExchangeRates);

    }

    @Test
    public void fetchExchangeRatesFailSuccess() throws Exception {
        DayExchangeRates dayExchangeRates = new DayExchangeRates();
        when(ecbExternalService.fetchExchangeRates())
                .thenThrow(new RuntimeException("Exception when processing data"))
                .thenReturn(dayExchangeRates);
        fixture.fetchExchangeRates();


        ArgumentCaptor<DayExchangeRates> exchangeRatesCaptor = ArgumentCaptor.forClass(DayExchangeRates.class);
        verify(exchangeRepository, times(1)).setDayExchangeRates(exchangeRatesCaptor.capture());

        assertThat("Exchange rates are different", exchangeRatesCaptor.getValue() == dayExchangeRates);

    }

}