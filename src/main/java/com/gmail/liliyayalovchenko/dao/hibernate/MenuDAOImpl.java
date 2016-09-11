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
}
