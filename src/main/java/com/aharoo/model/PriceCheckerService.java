package com.aharoo.model;

import com.aharoo.model.CryptoPrice.Price;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

@Service
public class PriceCheckerService {

    public Price priceChecker() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(new URL("https://api.coindesk.com/v1/bpi/currentprice.json"),Price.class);
    }

    public double getBitcoinPrice() throws IOException {
        return priceChecker().getBpi().getUsd().getRate_float();
    }

    public double getEuroPrice() throws IOException {
        return priceChecker().getBpi().getEur().getRate_float();
    }

    public double getPoundPrice() throws IOException {
        return priceChecker().getBpi().getGbp().getRate_float();
    }

}
