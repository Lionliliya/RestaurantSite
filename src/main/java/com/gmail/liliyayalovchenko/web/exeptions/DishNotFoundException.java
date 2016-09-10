package com.gmail.liliyayalovchenko.web.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Dish Not Found By This Id")
public class DishNotFoundException extends Exception {

    public DishNotFoundException(int id){
        super ("DishNotFoundException with id="+id);
    }

    public DishNotFoundException(String name){
        super ("DishNotFoundException with name="+name);
    }


}
