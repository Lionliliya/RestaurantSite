package com.gmail.liliyayalovchenko.dao;

import com.gmail.liliyayalovchenko.domain.Dish;

import java.util.List;

public interface DishDAO {

    Dish getDishByName(String dishName);

    List<Dish> search(String pattern);

}
