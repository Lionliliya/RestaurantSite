package com.gmail.liliyayalovchenko.web.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Menu Not Found By This Id")
public class MenuNotFoundException extends Exception {

    public MenuNotFoundException(int id) {
        super("MenuNotFoundException with id=" + id);
    }

    public MenuNotFoundException(String name) {
        super("MenuNotFoundException with name=" + name);
    }
}
