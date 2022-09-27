package com.cisco.currencyexchangeservice.controller;

import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class CircuitBreakerController {

    private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
    @Retry(name = "default", fallbackMethod = "hardCodedResponse")
    public String sampleApi() {
        logger.info("sample api call received!");
        ResponseEntity<String> entity = new RestTemplate().getForEntity("http://localhost:8080/dummy-entity", String.class);
        return entity.getBody();
    }

    public String hardCodedResponse(Exception ex) {
        return "fall-back response";
    }
}
