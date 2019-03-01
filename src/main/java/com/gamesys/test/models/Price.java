package com.gamesys.test.models;

import java.util.Date;

public class Price {

    private Integer id;
    private Date date;
    private Double open;
    private Double low;
    private Double high;
    private Double close;

    private Double average;
    private Boolean isRising;
    private Date createdAt;

    public Price(Date date, Double open, Double low, Double high, Double close){
        this.date = date;
        this.open = open;
        this.low = low;
        this.high = high;
        this.close = close;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public Boolean getRising() {
        return isRising;
    }

    public void setRising(Boolean rising) {
        isRising = rising;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
