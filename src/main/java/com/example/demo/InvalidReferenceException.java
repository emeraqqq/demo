package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidReferenceException extends IllegalArgumentException{
    public InvalidReferenceException(String reference){
        super(reference + " is not a correct reference." +
                "valid references are: adjclose,high,low,open,close,volume");
    }
}
