package com.gmail.liliyayalovchenko.dao.hibernate;

import com.gmail.liliyayalovchenko.dao.MenuDAO;
import com.gmail.liliyayalovchenko.domain.Dish;
import com.gmail.liliyayalovchenko.domain.Menu;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class MenuDAOImpl implements MenuDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void createMenu(Menu menu) {
        for (Dish dish : menu.getDishList()) {
            dish.setMenu(menu);
            System.out.println(" menu was set for dish " + dish.getName());
        }

        sessionFactory.getCurrentSession().save(menu);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void removeMenu(int id) {
        Session session = sessionFactory.getCurrentSession();
        Menu menu = session.load(Menu.class, id);
        session.delete(menu);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Menu getMenuByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Menu.class);
        criteria.add(Restrictions.eq("name", name));
        Menu menu = (Menu) criteria.list().get(0);
        if (menu == null) {
            throw new RuntimeException("Cant get Menu by this name! Wrong name!");
        } else {
            return menu;
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addDishToMenu(int menuId, Dish dish) {
        Session session = sessionFactory.getCurrentSession();
        Menu menu = session.load(Menu.class, menuId);
        if (menu == null) {
            throw new RuntimeException("Cant get menu by this id");
        } else {
            menu.addDishToMenu(dish);
            session.update(menu);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void removeDishFromMenu(int menuId, Dish dish) {
        Session session = sessionFactory.getCurrentSession();
        Menu menu = session.load(Menu.class, menuId);
        if (menu == null) {
            throw new RuntimeException("Cant get menu by this id");
        } else {
            menu.removeDishFromMenu(dish);
            session.update(menu);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void updateDish(Dish dishByName, Menu menu) {
        Session session = sessionFactory.getCurrentSession();
        dishByName.setMenu(menu);
        session.update(dishByName);
        session.flush();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Menu> getAllMenu() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select e from Menu e");
        List<Menu> menus = query.list();
        return menus;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Menu getMenuById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Menu menu = session.load(Menu.class, id);
        return menu;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void saveMenu(int id, String name) {
        Session session = sessionFactory.getCurrentSession();
        Menu menu = session.load(Menu.class, id);
        menu.setName(name);
        session.update(menu);
    }
}
