package com.gmail.liliyayalovchenko.dao;

import com.gmail.liliyayalovchenko.domain.Menu;
import java.util.List;

public interface MenuDAO {

    Menu getMenuByName(String name);

    List<Menu> getAllMenu();

    Menu getMenuById(int id);

}
