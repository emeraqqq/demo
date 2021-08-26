package com.example.demo;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StockController.class)
public class StockControllerTest {
    @MockBean
    StockServiceImp stockServiceImp;

    @MockBean
    StockRepository stockRepository;

    @Autowired
    MockMvc mockMvc;

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

    @BeforeEach
    public void initStockService(){
        stock.add(stock1);
//        System.out.println(stock);
        stock.add(stock2);
//        System.out.println(stocks);
        when(stockRepository.findAll()).thenReturn(stock);
        stockServiceImp.fillStocks();
    }

    @Test
    public void testGetStockSuccess() throws Exception{
        List<Stock> list = stock.subList(0,1);
        when(stockServiceImp.showStock("002007.SZ")).
                thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/stock/getStock/002007.SZ"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCalculateDailyPrice() throws Exception{
        when(stockServiceImp.calculateDailyPrice("002007.SZ", "2018-01-02"))
                .thenReturn(40.0);
        RequestBuilder request = MockMvcRequestBuilders
                .get("/stock/calculateDailyPrice/002007.SZ?date=2018-01-02");
        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    public void testCalculateDailyPriceNotFound() throws Exception{
        when(stockServiceImp.calculateDailyPrice("002007.SZ", "2018-01-01"))
                .thenThrow(InvalidDateException.class);
        RequestBuilder request = MockMvcRequestBuilders
                .get("/stock/calculateDailyPrice/002007.SZ?date=2018-01-01");
        mockMvc.perform(request).andExpect(status().is4xxClientError());
    }

    @Test
    public void testShowMaxPrice() throws Exception{
        when(stockServiceImp.showMaxPrice("002007.SZ"))
                .thenReturn(6.0);
        RequestBuilder request = MockMvcRequestBuilders.get("/stock/showMaxPrice/002007.SZ");
        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    public void testShowMinPrice() throws Exception{
        when(stockServiceImp.showMinPrice("002007.SZ"))
                .thenReturn(4.0);
        RequestBuilder request = MockMvcRequestBuilders.get("/stock/showMinPrice/002007.SZ");
        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    public void testHead() throws Exception{
        List<Stock> list = stock.subList(0,1);
        when(stockServiceImp.head(2, "002007.SZ")).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/stock/head/002007.SZ/2"))
                .andExpect(status().isOk());
    }

//    @Test
//    public void testGetUserSuccess() throws Exception{
//        when(userService.getUser(1L)).thenReturn(defaultUsers.get(0));
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.get("/api/v1/users/1"))
//                .andExpect(status().isOk());
//    }
}
