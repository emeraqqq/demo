package com.example.demo;

import com.example.demo.StockRepository;
import com.example.demo.StockServiceImp;
import org.junit.Before;
import org.junit.jupiter.api.*;

import java.util.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


@ExtendWith(MockitoExtension.class)
public class StockServiceImpTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServiceImp stockServiceImp;

    HashMap<String, ArrayList<Stock>> stocks = new HashMap<>();
    ArrayList<Stock> stock = new ArrayList<>();

    Stock stock1 = new Stock(1,
            "002007.SZ",
            "2018-01-02",
            1,
            2,
            1.5,
            3,
            4,
            10);
    Stock stock2 = new Stock(2,
            "002007.SZ",
            "2018-01-03",
            2,
            3,
            2.4,
            4,
            6,
            20
    );

//    Stock stock = mock(Stock.class);

    @BeforeEach
    public void initStockService(){
        stock.add(stock1);
//        System.out.println(stock);
        stock.add(stock2);
//        System.out.println(stocks);
        when(stockRepository.findAll()).thenReturn(stock);
        stockServiceImp.fillStocks();
    }

    //mock一个stocks的具体数值
    @Test
    public void testSHowStockWithWrongSymbol(){
        boolean thrown = false;
        try{
            stockServiceImp.showStock("002007.SS");
        }catch (IllegalArgumentException e){
            thrown = true;
        }
        assertTrue("Symbol 002007.SS is not a valid symbol", thrown);
    }
    @Test
    public void testShowStock(){
//        when(stock.getSymbol()).thenReturn("002007.SZ");
//        when(stockService.checkSymbol("002008.SZ")).thenReturn(true);

        ArrayList stockstest = (ArrayList) stockServiceImp.showStock("002007.SZ");
        assertNotNull(stockstest);
    }

    @Test
    public void testCalculateDailyPriceWithWrongDate(){
        boolean thrown = false;
        try{
            stockServiceImp.calculateDailyPrice("002007.SZ",  "2018-01-01");
        }catch (IllegalArgumentException e){
            thrown = true;
        }
        assertTrue("Date 2018-01-01 is not a valid date,it is either not in the data or date range is invalid", thrown);
    }

    @Test
    public void testCalculateDailyPrice(){
        double result = stockServiceImp.calculateDailyPrice("002007.SZ", "2018-01-02");
        assertEquals(40.0, result,0);
    }

    @Test
    public void testCalculateAverageWithWrongDate(){
        boolean thrown = false;
        try{
            stockServiceImp.calculateAverage("002007.SZ", 2, "2018-01-01");
        }catch (IllegalArgumentException e){
            thrown = true;
        }
        assertTrue("Date 2018-01-01 is not a valid date,it is either not in the data or date range is invalid", thrown);
    }
    @Test
    public void testCalculateAverage(){
        double result = stockServiceImp.calculateAverage("002007.SZ", 2,"2018-01-02");
        assertEquals(5.0, result, 0);
    }

    @Test
    public void testShowMaxPrice(){
        double result = stockServiceImp.showMaxPrice("002007.SZ");
        assertEquals(6, result, 0);
    }

    @Test
    public void testShowMinPrice(){
        double result = stockServiceImp.showMinPrice("002007.SZ");
        assertEquals(4, result, 0);
    }

    @Test
    public void testHead(){
        List<Stock> result = stockServiceImp.head(1, "002007.SZ");
        assertNotNull(result);
        assertTrue(result.size() == 1);
    }

    @Test
    public void testTail(){
        List<Stock> result = stockServiceImp.tail(1, "002007.SZ");
        assertNotNull(result);
        assertTrue(result.size() == 1);
    }

}
