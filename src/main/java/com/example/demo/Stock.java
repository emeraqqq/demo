package com.example.demo;

import javax.persistence.*;

@Entity
@Table

public class Stock {
    private String symbol;

    @Id
    private String Date;

    @Column(nullable = false)
    private double open;
    @Column(nullable = false)
    private double high;
    @Column(nullable = false)
    private double low;
    @Column(nullable = false)
    private double close;
    @Column(nullable = false)
    private double adjclose;
    @Column(nullable = false)
    private double volume;

    public Stock(String symbol, String date, double open, double high, double low, double close, double adjclose, double volume) {
        this.symbol = symbol;
        this.Date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.adjclose = adjclose;
        this.volume = volume;

    }

    public Stock(String date, double volume, double adjclose) {
        this.Date = date;
        this.volume = volume;
        this.adjclose = adjclose;
    }

    public Stock() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getAdjclose() {
        return adjclose;
    }

    public void setAdjclose(double adjclose) {
        this.adjclose = adjclose;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "symbol=" + symbol +
                ", Date='" + Date + '\'' +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                ", adjclose=" + adjclose +
                ", volume=" + volume +
                '}';
    }
}
