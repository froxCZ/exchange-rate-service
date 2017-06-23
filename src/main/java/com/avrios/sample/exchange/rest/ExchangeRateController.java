package com.avrios.sample.exchange.rest;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avrios.sample.exchange.model.ExchangeRate;
import com.avrios.sample.exchange.service.ExchangeService;
import com.avrios.sample.exchange.util.DateUtil;

@RestController
public class ExchangeRateController {
    @Autowired
    ExchangeService exchangeService;

    @RequestMapping(path = "/EUR/{currency}", method = RequestMethod.GET)
    public ExchangeRate updateContact(@PathVariable(value = "currency") String currency,
                                      @RequestParam("date") @DateTimeFormat(pattern = DateUtil.DATE_FORMAT) Date date) {
        return exchangeService.getExchangeRate(currency, date);
    }

}
