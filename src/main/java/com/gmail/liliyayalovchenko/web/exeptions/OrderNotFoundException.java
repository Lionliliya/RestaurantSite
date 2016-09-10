package com.gmail.liliyayalovchenko.web.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Order Not Found By This Property")
public class OrderNotFoundException extends Exception {

    public OrderNotFoundException(int id) {
        super ("OrderNotFoundException with id="+id);
    }

    public OrderNotFoundException(String property) {
        super ("OrderNotFoundException with property="+property);
    }
}
