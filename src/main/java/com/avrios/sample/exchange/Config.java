package com.avrios.sample.exchange;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.avrios.sample.exchange.util.DateUtil;

@Configuration
public class Config {
    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        Jackson2ObjectMapperBuilder b = new Jackson2ObjectMapperBuilder();
        b.simpleDateFormat(DateUtil.DATE_FORMAT);
        return b;
    }

}