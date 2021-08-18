package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidSymbolException extends IllegalArgumentException{
    public InvalidSymbolException(String symbol){
        super("Symbol " + symbol + " is not a valid symbol");
    }
}
