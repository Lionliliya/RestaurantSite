package com.gmail.liliyayalovchenko.dao.hibernate;

import com.gmail.liliyayalovchenko.dao.DishDAO;
import com.gmail.liliyayalovchenko.domain.Dish;
import com.gmail.liliyayalovchenko.domain.DishCategory;
import com.gmail.liliyayalovchenko.domain.Ingredient;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DishDAOImpl implements DishDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Dish getDishByName(String dishName) {
        Session session = sessionFactory.getCurrentSession();

        Dish dish = (Dish) session.createQuery("select d from Dish d where d.name = :var")
                .setParameter("var", dishName)
                .uniqueResult();
        if (dish != null) {
            return dish;
        } else {
            throw new RuntimeException("Cant get dish by this dish name! Error!");
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Dish> search(String pattern) {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT d FROM Dish d WHERE d.name LIKE :pattern", Dish.class);
        query.setParameter("pattern", "%" + pattern + "%");
        return (List<Dish>) query.getResultList();
    }
}
