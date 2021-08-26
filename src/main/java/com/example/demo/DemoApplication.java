package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

//主入口
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);

        StockServiceImp stockService = ctx.getBean("StockService", StockServiceImp.class);
        stockService.fillStocks();
    }

    @Bean
    public ArrayList<Stock> loadData(StockRepository stockRepository) {
        String[] tmp = {"002007.SZ","510500.SS"};
        ArrayList<Stock> stock = new ArrayList<Stock>();
        try {
            //(文件完整路径),编码格式
            int count = 1;
            for(String str:tmp){
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/" + str + ".csv"), "utf-8"));
                String line = reader.readLine();
                String item[];
                while ((line = reader.readLine()) != null) {
                    if(str.equals("510500.SS")){
                        item = line.split(",");
                    }else{
                        item = line.split("\t");//CSV格式文件时候的分割符,我使用的是\t
                    }
                    stock.add(new Stock(count,str,item[0],
                            Double.parseDouble(item[1]),
                            Double.parseDouble(item[2]),
                            Double.parseDouble(item[3]),
                            Double.parseDouble(item[4]),
                            Double.parseDouble(item[5]),
                            Double.parseDouble(item[6])));
                    count++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*for (int i = 0; i < stock.size(); i++) {
            System.out.println(stock);
        }*/
        stockRepository.saveAll(stock);
        return stock;
    }



}
