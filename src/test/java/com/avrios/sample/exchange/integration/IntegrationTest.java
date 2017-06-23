package com.avrios.sample.exchange.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.JsonNode;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void basicTest() throws InterruptedException {

        Thread.sleep(3000);//wait for fetching data from mocked ecb service

        ResponseEntity<JsonNode> response = getRate("CZK", null);
        assertThat(response.getBody().get("rate").asDouble(), is(26.27));
        assertThat(response.getBody().get("date").asText(), is("2017-06-22"));
        assertThat(response.getBody().get("currency").asText(), is("CZK"));


        response = getRate("USD", "2017-05-05");
        assertThat(response.getBody().get("rate").asDouble(), is(1.0961));
        assertThat(response.getBody().get("date").asText(), is("2017-05-05"));
        assertThat(response.getBody().get("currency").asText(), is("USD"));
    }

    ResponseEntity<JsonNode> getRate(String currency, String date) {
        String param = date == null ? "" : "?date=" + date;
        ResponseEntity<JsonNode> response = restTemplate
                .getForEntity("/exchange-rate/EUR/" + currency + param,
                        JsonNode.class);
        return response;
    }

}