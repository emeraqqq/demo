package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InputIntegerOutOfRangeException extends IllegalArgumentException{
    public InputIntegerOutOfRangeException(Integer number){
        super("input number: "+ number +" out of range");
    }
}
