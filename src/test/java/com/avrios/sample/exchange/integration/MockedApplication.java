package com.avrios.sample.exchange.integration;

import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.avrios.sample.exchange.Application;

import static com.avrios.sample.exchange.TestUtil.readResourceFile;
import static org.mockito.Mockito.when;

@SpringBootApplication
public class MockedApplication extends Application {
    public static void main(String[] args) {
        SpringApplication.run(MockedApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        when(restTemplate.getForEntity("http://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml", String.class))
                .thenAnswer(i -> new ResponseEntity<String>(
                        readResourceFile("/eurofxref-hist-90d.xml"),
                        HttpStatus.OK)
                );
        return restTemplate;
    }
}