package com.gmail.liliyayalovchenko.service;

import com.gmail.liliyayalovchenko.dao.MenuDAO;
import com.gmail.liliyayalovchenko.domain.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class MenuService {

    @Autowired
    private MenuDAO menuDAO;

    @Transactional
    public List<Menu> getAllMenus() {
        return menuDAO.getAllMenu();
    }

    @Transactional
    public Menu getMenuByName(String menuName) {
        return menuDAO.getMenuByName(menuName);
    }

    @Transactional
    public Menu getMenuById(int id) {
       return menuDAO.getMenuById(id);
    }
}
