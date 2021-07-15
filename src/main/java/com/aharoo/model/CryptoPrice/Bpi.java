package com.aharoo.model.CryptoPrice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "USD",
        "GBP",
        "EUR"
})
public class Bpi {

    @JsonProperty("USD")
    private Currency usd;
    @JsonProperty("GBP")
    private Currency gbp;
    @JsonProperty("EUR")
    private Currency eur;

    public Bpi(Currency usd, Currency gbp, Currency eur) {
        this.usd = usd;
        this.gbp = gbp;
        this.eur = eur;
    }

    public Bpi(){}

    public Currency getUsd() {
        return usd;
    }

    public void setUsd(Currency usd) {
        this.usd = usd;
    }

    public Currency getGbp() {
        return gbp;
    }

    public void setGbp(Currency gbp) {
        this.gbp = gbp;
    }

    public Currency getEur() {
        return eur;
    }

    public void setEur(Currency eur) {
        this.eur = eur;
    }
}
