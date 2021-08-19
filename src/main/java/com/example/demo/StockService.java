package com.example.demo;

import java.util.List;

public interface StockService {
    List<Stock> showStock(String symbol);
    double calculateDailyPrice(String symbol,String date);
    double calculateAverage(String symbol,int days,String date);
    double showMaxPrice(String symbol);
    double showMinPrice(String symbol);
    List<Stock> head(int number,String symbol);
    List<Stock> tail(int number,String symbol);
    List<Stock> groupByDay(int number,String symbol);
    List<Stock> groupByMonth(String symbol);
    List<Stock> groupByWeek(String symbol);
    List<Stock> sortStock(String reference,int reverse,String symbol);
    double calculateStd(String symbol, int days, String date);
}
