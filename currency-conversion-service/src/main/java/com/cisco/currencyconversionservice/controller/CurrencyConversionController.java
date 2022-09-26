package com.cisco.currencyconversionservice.controller;

import com.cisco.currencyconversionservice.model.CurrencyConversion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {

    @GetMapping("/currency-conversion-controller/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(@PathVariable String from,
                                                          @PathVariable String to,
                                                          @PathVariable BigDecimal quantity) {

        Map<String, String> uriVaribles = new HashMap<>();
        uriVaribles.put("from", from);
        uriVaribles.put("to", to);

        /* Call currency-exchange-service */
        ResponseEntity<CurrencyConversion> response = new RestTemplate()
                                .getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                                                    CurrencyConversion.class,
                                                    uriVaribles);

        CurrencyConversion cc = response.getBody();

        return new CurrencyConversion(cc.getId(), from, to, quantity,
                cc.getConversionMultiple(),
                quantity.multiply(cc.getConversionMultiple()),
                cc.getEnvironment());

    }
}
