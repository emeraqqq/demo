package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDateException extends IllegalArgumentException{
    public InvalidDateException(String date){
        super("Date " + date + " is not a valid date,it is either not in the data or date range is invalid");
    }
}
