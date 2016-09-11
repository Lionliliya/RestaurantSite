package com.gmail.liliyayalovchenko.service;

import com.gmail.liliyayalovchenko.dao.DishDAO;
import com.gmail.liliyayalovchenko.domain.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DishService {

    @Autowired
    private DishDAO dishDAO;

    @Transactional
    public Dish getDishByName(String dishName) {
        return dishDAO.getDishByName(dishName);
    }

    @Transactional
    public List<Dish> search(String pattern) {
        return dishDAO.search(pattern);
    }

    @Transactional
    public void remove(int id) {
        dishDAO.remove(id);
    }
}
