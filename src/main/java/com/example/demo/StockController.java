package com.example.demo;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/stock")
public class StockController {
    @Autowired
    StockServiceImp stockServiceImp;

    public StockController(StockServiceImp stockServiceImp) {
        this.stockServiceImp = stockServiceImp;
    }

    @GetMapping(value="/getStock/{symbol}", produces = MediaType.APPLICATION_XML_VALUE)
    public List<Stock> getStock(@PathVariable("symbol") String symbol){
        return stockServiceImp.showStock(symbol);
    }

    @GetMapping(value="/calculateDailyPrice/{symbol}")
    public double calculateDailyPrice(@PathVariable("symbol") String symbol, @RequestParam("date")String date) {
        return stockServiceImp.calculateDailyPrice(symbol, date);
    }

    @GetMapping(value="/calculateAverage/{symbol}")
    public double calculateAverage(@PathVariable("symbol") String symbol,@RequestParam("days") Integer days,@RequestParam("date")String date) {
        return stockServiceImp.calculateAverage(symbol,days,date);
    }

    @GetMapping(value="/showMaxPrice/{symbol}")
    public double showMaxPrice(@PathVariable("symbol") String symbol) {
        return stockServiceImp.showMaxPrice(symbol);
    }

    @GetMapping(value = "/showMinPrice/{symbol}")
    public double showMinPrice (@PathVariable("symbol") String symbol){
        return stockServiceImp.showMinPrice(symbol);
    }

    @GetMapping(value="/head/{symbol}/{number}",produces = MediaType.APPLICATION_XML_VALUE)
    public List<Stock> head(@PathVariable("number") int number,
                            @PathVariable("symbol")String symbol){
        return stockServiceImp.head(number,symbol);
    }

    @GetMapping(value="/tail/{symbol}/{number}",produces = MediaType.APPLICATION_XML_VALUE)
    public List<Stock> tail(@PathVariable("number") int number,
                            @PathVariable("symbol")String symbol) {
        return stockServiceImp.tail(number,symbol);
    }

    @GetMapping("group/day/{symbol}/{days}")
    public List<Stock> groupByDay(@PathVariable("days") int number,
                                  @PathVariable("symbol") String symbol){
        return stockServiceImp.groupByDay(number,symbol);
    }

    @GetMapping("group/month/{symbol}")
    public List<Stock> groupByDay(@PathVariable("symbol")String symbol){
        return stockServiceImp.groupByMonth(symbol);
    }

    @GetMapping("group/week/{symbol}")
    public List<Stock> groupByWeek(@PathVariable("symbol")String symbol){
        return stockServiceImp.groupByWeek(symbol);
    }

    @GetMapping(value = "/sort/{symbol}",produces = MediaType.APPLICATION_XML_VALUE)
    public List<Stock> sortStock(@RequestParam("reference")String reference,
                                 @RequestParam("reverse")int reverse,
                                 @PathVariable("symbol")String symbol){
        return stockServiceImp.sortStock(reference,reverse,symbol);
    }

    @GetMapping(value="/calculateStd/{symbol}")
    public double calculateStd(@PathVariable("symbol") String symbol,@RequestParam("days")Integer days,@RequestParam("date")String date) {
        return stockServiceImp.calculateStd(symbol,days,date);
    }

    @GetMapping(value = "/showOhlcv/{symbol}")
    public List<Double> showOHLCVol(@RequestParam("ohlcv") String ohlcv,
                                    @PathVariable("symbol") String symbol){
        return stockServiceImp.showOHLCVol(ohlcv,symbol);
    }

    @GetMapping(value = "/showFilter/{symbol}")
    public List<Stock> showFilter(@RequestParam("filter") String filter,
                                  @RequestParam("num") Integer num,
                                  @PathVariable("symbol")String symbol){
        return stockServiceImp.showFilter(filter, num,symbol);
    }
}