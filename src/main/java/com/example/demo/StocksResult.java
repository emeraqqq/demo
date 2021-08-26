package com.example.demo;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class StocksResult {
    private List<Stock> result;

    StocksResult(){
    }

    public List<Stock> getResult() {
        return result;
    }

    public void setResult(List<Stock> result) {
        this.result = result;
    }
}
