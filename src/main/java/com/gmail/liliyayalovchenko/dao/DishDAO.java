package com.gmail.liliyayalovchenko.dao;

import com.gmail.liliyayalovchenko.domain.Dish;
import com.gmail.liliyayalovchenko.domain.Ingredient;

import java.util.List;

public interface DishDAO {

    void save(Dish dish);

    void removeDish(Dish dish);

    List<Dish> findAll();

    Dish getDishByName(String dishName);

    Dish getDishById(int dishId);

    List<Dish> search(String pattern);

    void saveDish(int id, String name, String dishCategory, double price, int weight, String photoLink);

    void saveDish(String name, String dishCategory, double price, int weight, String photoLink, List<Ingredient> ingredients);

    void remove(int id);
}
