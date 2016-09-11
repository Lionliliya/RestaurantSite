package com.gmail.liliyayalovchenko.dao;


import com.gmail.liliyayalovchenko.domain.Dish;
import com.gmail.liliyayalovchenko.domain.Menu;

import java.util.List;

public interface MenuDAO {

    void createMenu(Menu menu);

    void removeMenu(int menuId);

    Menu getMenuByName(String name);

    void addDishToMenu(int menuId, Dish dish);

    void removeDishFromMenu(int menuId, Dish dish);

    List<Menu> getAllMenu();

    Menu getMenuById(int id);

    void saveMenu(int id, String name);

    void updateDish(Dish dishByName, Menu menu);

}
