package com.aharoo.model.CryptoPrice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "code",
        "symbol",
        "rate",
        "description",
        "rate_float"
})
public class Currency {
    @JsonProperty("code")
    private String code;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("rate")
    private String rate;
    @JsonProperty("description")
    private String description;
    @JsonProperty("rate_float")
    private double rate_float;

    public Currency(String code, String symbol, String rate, String description, double rate_float) {
        this.code = code;
        this.symbol = symbol;
        this.rate = rate;
        this.description = description;
        this.rate_float = rate_float;
    }

    public Currency(){}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRate_float() {
        return rate_float;
    }

    public void setRate_float(double rate_float) {
        this.rate_float = rate_float;
    }
}
