package com.avrios.sample.exchange.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.avrios.sample.exchange.repository.ExchangeRepository;

@RestControllerAdvice
public class ExceptionControllerHandler {
    @ExceptionHandler(value = {ExchangeRepository.ExchangeRateNotFound.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> rateNotFound(ExchangeRepository.ExchangeRateNotFound e) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", e.getMessage());
        return responseBody;
    }

    @ExceptionHandler(value = {Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> unknownException() {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Internal server error.");
        return responseBody;
    }
}
