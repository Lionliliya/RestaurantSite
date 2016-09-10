package com.gmail.liliyayalovchenko.service;

import com.gmail.liliyayalovchenko.dao.DishDAO;
import com.gmail.liliyayalovchenko.dao.MenuDAO;
import com.gmail.liliyayalovchenko.domain.Dish;
import com.gmail.liliyayalovchenko.domain.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class MenuService {

    @Autowired
    private MenuDAO menuDAO;
    @Autowired
    private DishDAO dishDAO;
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuService.class);

    @Transactional
    public List<Menu> getAllMenus() {
        LOGGER.info("Try to get all menus");
        List<Menu> menus = menuDAO.getAllMenu();
        LOGGER.info("All menus are got");
        return menus;
    }

    @Transactional
    public Menu getMenuByName(String menuName) {
        return menuDAO.getMenuByName(menuName);
    }

    @Transactional
    public Menu getMenuById(int id) {
        LOGGER.info("Try to get menu by id");
        Menu menu = menuDAO.getMenuById(id);
        LOGGER.info("Menu is got");
        return menu;
    }

    @Transactional
    public void saveMenu(int id, String name) {
        LOGGER.info("Try to save edited menu");
        menuDAO.saveMenu(id, name);
        LOGGER.info("Edited menu was successfully saved");
    }

    @Transactional
    public void removeMenu(int menu) {
        LOGGER.info("Try to remove menu");
        menuDAO.removeMenu(menu);
        LOGGER.info("Menu was removed");
    }

    @Transactional
    public void removeDishFromMenu(int menuId, Dish dish) {
        LOGGER.info("Try to remove dish from menu");
        menuDAO.removeDishFromMenu(menuId, dish);
        LOGGER.info("Dish from menu was removed");
    }

    @Transactional
    public void addDishToMenu(int menuId, Dish dish) {
        LOGGER.info("Try to add dish to menu");
        menuDAO.addDishToMenu(menuId, dish);
        LOGGER.info("Dish was added");
    }
    @Transactional
    public void updateDish(Dish dishByName, Menu menu) {
        menuDAO.updateDish(dishByName, menu);
    }

    @Transactional
    public void createMenu(Menu menu) {
        menuDAO.createMenu(menu);
    }

}
