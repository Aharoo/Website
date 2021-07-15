package com.aharoo.model.CryptoPrice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "time",
        "chartName",
        "bpi"
})
public class Price {
    @JsonProperty("time")
    private Time time;
    @JsonIgnore
    private String disclaimer;
    @JsonProperty("chartName")
    private String chartName;
    @JsonProperty("bpi")
    private Bpi bpi;

    public Price(Time time, String disclaimer, String chartName, Bpi bpi) {
        this.time = time;
        this.disclaimer = disclaimer;
        this.chartName = chartName;
        this.bpi = bpi;
    }

    public Price(){}

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public Bpi getBpi() {
        return bpi;
    }

    public void setBpi(Bpi bpi) {
        this.bpi = bpi;
    }

    @Override
    public String toString() {
        return "Price{" +
                "time=" + time +
                ", disclaimer='" + disclaimer + '\'' +
                ", chartName='" + chartName + '\'' +
                ", bpi=" + bpi +
                '}';
    }
}
