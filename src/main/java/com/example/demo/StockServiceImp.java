package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("StockService")
public class StockServiceImp implements StockService {
    private final StockRepository stockRepository;

    HashMap<String,ArrayList<Stock>> stocks;

    @Autowired
    public StockServiceImp(StockRepository stockRepository){
        this.stockRepository = stockRepository;
    }

//    @PostConstruct
    public void fillStocks(){
        ArrayList<Stock> data = (ArrayList<Stock>) stockRepository.findAll();
        stocks = new HashMap<>();
        for (Stock stock:data){
            String symbol = stock.getSymbol();
            ArrayList<Stock> tmp;
            if(stocks.containsKey(symbol)){
                tmp = stocks.get(symbol);
            }else{
                tmp = new ArrayList<Stock>();
            }
            tmp.add(stock);
            stocks.put(symbol,tmp);
        }
    }

    @Override
    public List<Stock> showStock(String symbol) {
        checkSymbol(symbol);
        return stocks.get(symbol);
    }

    @Override
    public double calculateDailyPrice(String symbol, String date) {
        checkSymbol(symbol);
        double result=0;
        for(Stock item: stocks.get(symbol)) {
            if (symbol.compareTo(item.getSymbol())==0 && date.compareTo(item.getDate()) == 0){
                result = item.getVolume() * item.getAdjclose();
            }
        }
        if(result == 0){
            throw new InvalidDateException(date);
        }
        return result;
    }

    @Override
    public double calculateAverage(String symbol,int days,String date) {
        checkSymbol(symbol);
        ArrayList<Stock> tmp = stocks.get(symbol);
        if(date.compareTo(tmp.get(0).getDate()) < 0){
            throw new InvalidDateException(date);
        }
        double result=0;int count=0;
        for(Stock item: tmp){
            if(symbol.compareTo(item.getSymbol())==0&&date.compareTo(item.getDate())<=0&&count<=days){
                result+=item.getAdjclose();
                count++;
            }
        }
        if(result == 0){
            throw new InvalidDateException(date);
        }
        result=result/count;
        return result;
    }

    @Override
    public double showMaxPrice(String symbol) {
        checkSymbol(symbol);
        double max = 0;
        for (Stock item : stocks.get(symbol)) {
            if (max < item.getAdjclose()) {
                max = item.getAdjclose();
            }
        }
        return max;
    }

    @Override
    public double showMinPrice(String symbol) {
        checkSymbol(symbol);
        double min = 9999;
        for (Stock item : stocks.get(symbol)) {
            if ( min > item.getAdjclose()) {
                min = item.getAdjclose();
            }
        }
        return min;

    }

    public List<Stock> head(int number,String symbol){
        checkSymbol(symbol);
        ArrayList<Stock> tmp = stocks.get(symbol);
        if(number < 0 || number > tmp.size()){
            throw new InputIntegerOutOfRangeException(number);
        }
        return tmp.subList(0,number);
    }

    @Override
    public List<Stock> tail(int number,String symbol) {
        checkSymbol(symbol);
        ArrayList<Stock> tmp = stocks.get(symbol);
        int size = tmp.size();
        if(size - number < 0 || number < 0){
            throw new InputIntegerOutOfRangeException(number);
        }
        return tmp.subList(size - number,size);
    }

    @Override
    public List<Stock> groupByDay(int number,String symbol) {
        checkSymbol(symbol);
        if(number < 1){
            throw new InputIntegerOutOfRangeException(number);
        }
        ArrayList<Stock> tmp = new ArrayList<>();
        double op=0,hi=0,lo=0,cl=0,adjc=0,v=0;
        int count = 0;
        String data="",date="";
        for(Stock info: stocks.get(symbol)){
            if(count == 0){
                data = info.getDate() + " ";
            }
            count++;
            op+=info.getOpen(); hi+=info.getHigh(); lo+=info.getLow();cl+=info.getClose();
            adjc+=info.getAdjclose(); v+=info.getVolume();date=info.getDate();
            if(count == number){
                data += date;
                tmp.add(new Stock(1,"1",data,op,hi,lo,cl,adjc,v));
                count=0;op=0;hi=0;lo=0;cl=0;adjc=0;v=0;
            }
        }
        data += date;
        tmp.add(new Stock(1,"1",data,op,hi,lo,cl,adjc,v));
        return tmp;
    }

    @Override
    public List<Stock> groupByMonth(String symbol) {
        checkSymbol(symbol);
        ArrayList<Stock> tmp = new ArrayList<>();
        double op=0,hi=0,lo=0,cl=0,adjc=0,v=0;
        String prevDate="",data="";
        for(Stock info: stocks.get(symbol)){
            if (data.length() == 0){
                prevDate = info.getDate();
                data = prevDate + " - ";
            }
            String month = info.getDate().substring(5,7);
            if(prevDate.substring(5,7).compareTo(month) != 0){
                data += prevDate;
                tmp.add(new Stock(1,"1",data,op,hi,lo,cl,adjc,v));
                op=0;hi=0;lo=0;cl=0;adjc=0;v=0;
                data = info.getDate() + " - ";
            }
            prevDate = info.getDate();
            op+=info.getOpen(); hi+=info.getHigh(); lo+=info.getLow();cl+=info.getClose();
            adjc+=info.getAdjclose(); v+=info.getVolume();
        }
        data += prevDate;
        tmp.add(new Stock(1,"1",data,op,hi,lo,cl,adjc,v));
        return tmp;
    }

    @Override
    public List<Stock> groupByWeek(String symbol) {
        checkSymbol(symbol);
        ArrayList<Stock> tmp = new ArrayList<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd",Locale.ENGLISH);
            Calendar prevCal = Calendar.getInstance(),currCal = Calendar.getInstance();
            double op=0,hi=0,lo=0,cl=0,adjc=0,v=0;
            String prevDate="",data="";
            for(Stock info: stocks.get(symbol)){
                if (data.length() == 0){
                    prevDate = info.getDate();
                    data = prevDate + " - ";
                    prevCal.setTime(sdf.parse(info.getDate()));
                }
                currCal.setTime(sdf.parse(info.getDate()));
                if(currCal.get(Calendar.DAY_OF_WEEK) < prevCal.get(Calendar.DAY_OF_WEEK)){
                    data += prevDate;
                    tmp.add(new Stock(1,"1",data,op,hi,lo,cl,adjc,v));
                    op=0;hi=0;lo=0;cl=0;adjc=0;v=0;
                    data = info.getDate() + " - ";
                }
                prevCal.setTime(sdf.parse(info.getDate()));
                prevDate = info.getDate();
                op+=info.getOpen(); hi+=info.getHigh(); lo+=info.getLow();cl+=info.getClose();
                adjc+=info.getAdjclose(); v+=info.getVolume();
            }
            data += prevDate;
            tmp.add(new Stock(1,"1",data,op,hi,lo,cl,adjc,v));
        }catch(ParseException e){}
        return tmp;
    }

    public List<Stock> sortStock(String reference,int reverse,String symbol){
        checkSymbol(symbol);
        ArrayList<Stock> sorted = new ArrayList<>(stocks.get(symbol));
        int flag = reverse == 1 ? -1 : 1;
        switch (reference){
            case "adjclose":
                Collections.sort(sorted, (o1, o2) -> {
                    if(o1.getAdjclose() < o2.getAdjclose()){return 1 * flag;}
                    else if(o1.getAdjclose() > o2.getAdjclose()){return -1 * flag;}
                    else{return 0;}});
                break;
            case "open":
                Collections.sort(sorted, (o1, o2) -> {
                    if(o1.getOpen() < o2.getOpen()){return 1 * flag;}
                    else if(o1.getOpen() > o2.getOpen()){return -1 * flag;}
                    else{return 0;}});
                break;
            case "close":
                Collections.sort(sorted, (o1, o2) -> {
                    if(o1.getClose() < o2.getClose()){return 1 * flag;}
                    else if(o1.getClose() > o2.getClose()){return -1 * flag;}
                    else{return 0;}});
                break;
            case "low":
                Collections.sort(sorted, (o1, o2) -> {
                    if(o1.getLow() < o2.getLow()){return 1 * flag;}
                    else if(o1.getLow() > o2.getLow()){return -1 * flag;}
                    else{return 0;}});
                break;
            case "high":
                Collections.sort(sorted, (o1, o2) -> {
                    if(o1.getHigh() < o2.getHigh()){return 1 * flag;}
                    else if(o1.getHigh() > o2.getHigh()){return -1 * flag;}
                    else{return 0;}});
                break;
            case "volume":
                Collections.sort(sorted, (o1, o2) -> {
                    if(o1.getVolume() < o2.getVolume()){return 1 * flag;}
                    else if(o1.getVolume() > o2.getVolume()){return -1 * flag;}
                    else{return 0;}});
                break;
            default:
                throw new InvalidReferenceException(reference);
        }
        return sorted;
    }

    @Override
    public double calculateStd(String symbol, int days, String date) {
        checkSymbol(symbol);
        double std = 0;
        int count = 0;
        double average = calculateAverage(symbol, days, date);
        for(Stock item: stocks.get(symbol)) {
            if(symbol.compareTo(item.getSymbol())==0 && date.compareTo(item.getDate()) <= 0 && count <= days) {
                std += Math.pow((item.getAdjclose() - average), 2 );
                count++;
            }
        }
        std = Math.sqrt(std/count);
        return std;
    }

    public List<Double> showOHLCVol(String ohlcv,String symbol) {
        checkSymbol(symbol);
        ArrayList<Stock> stocks = this.stocks.get(symbol);
        ArrayList<Double> tmp = new ArrayList<>();
        switch (ohlcv) {
            case "open":
                for(Stock item: stocks) {
                    tmp.add(item.getOpen());
                };
                break;
            case "high":
                for(Stock item: stocks) {
                    tmp.add(item.getHigh());
                };
                break;
            case "low":
                for(Stock item: stocks) {
                    tmp.add(item.getLow());
                };
                break;
            case "close":
                for(Stock item: stocks) {
                    tmp.add(item.getClose());
                };
                break;
            case "volume":
                for(Stock item: stocks) {
                    tmp.add(item.getVolume());
                };
                break;
            default:
                throw new InvalidReferenceException(ohlcv);
        }
        return tmp;
    }


    public List<Stock> showFilter(String filter, Integer num,String symbol) {
        checkSymbol(symbol);
        if(num<0){
            throw new InputIntegerOutOfRangeException(num);
        }
        ArrayList<Stock> stocks = this.stocks.get(symbol);
        ArrayList<Stock> tmp = new ArrayList<>();
        switch (filter) {
            case "open":
                for(Stock item : stocks){
                    if(item.getOpen() <= num){
                        tmp.add(item);
                    }
                }
                break;
            case "high":
                for(Stock item : stocks){
                    if(item.getHigh() <= num){
                        tmp.add(item);
                    }
                }
                break;
            case "low":
                for(Stock item : stocks){
                    if(item.getLow() <= num){
                        tmp.add(item);
                    }
                }
                break;
            case "close":
                for(Stock item : stocks){
                    if(item.getClose() <= num){
                        tmp.add(item);
                    }
                }
                break;
            case "adjclose":
                for(Stock item : stocks){
                    if(item.getAdjclose() <= num){
                        tmp.add(item);
                    }
                }
                break;
            case "volume":
                for(Stock item : stocks){
                    if(item.getVolume() <= num){
                        tmp.add(item);
                    }
                }
                break;
            default:
                throw new InvalidReferenceException(filter);
        }
        return tmp;
    }

    private void checkSymbol(String symbol){
        if(!stocks.containsKey(symbol)){
            throw new InvalidSymbolException(symbol);
        }
    }
}
