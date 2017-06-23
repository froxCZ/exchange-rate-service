package com.avrios.sample.exchange.service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import com.avrios.sample.exchange.model.DayExchangeRates;

import static com.avrios.sample.exchange.TestUtil.readResourceFile;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EcbExternalServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EcbExternalService fixture;

    @Test
    public void fetchExchangeRates() throws Exception {
        when(restTemplate.getForObject("http://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml", String.class))
                .thenAnswer(i -> readResourceFile("/eurofxref-hist-90d.xml"));

        DayExchangeRates ratesTable = fixture.fetchExchangeRates();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for (Object[] testCase : testCases()) {
            Date date = formatter.parse((String) testCase[0]);
            assertThat(ratesTable.get(date).get(testCase[1]), is((double) testCase[2]));
        }
    }

    private List<Object[]> testCases() {
        return Arrays.asList(new Object[][] {
                {"2017-06-20", "USD", 1.1156},
                {"2017-06-20", "BGN", 1.9558},
                {"2017-06-20", "CZK", 26.293},

                {"2017-05-29", "HUF", 307.84},

                {"2017-03-27", "TRY", 3.9203},
        });
    }


}