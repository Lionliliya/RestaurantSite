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

//import javax.persistence.Query;

public class DishDAOImpl implements DishDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void save(Dish dish) {
        sessionFactory.getCurrentSession().save(dish);
    }


    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void saveDish(int id, String name, String dishCategory, double price, int weight, String photoLink) {
        Session session = sessionFactory.getCurrentSession();
        Dish dish = session.load(Dish.class, id);
        createDish(name, dishCategory, price, weight, photoLink, dish);
        session.update(dish);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void saveDish(String name, String dishCategory, double price, int weight, String photoLink, List<Ingredient> ingredients) {
        Session session = sessionFactory.getCurrentSession();
        Dish dish = new Dish();
        dish.setIngredients(ingredients);
        createDish(name, dishCategory, price, weight, photoLink, dish);
        session.save(dish);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Dish> findAll() {
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("select d from Dish d");
        return query.getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Dish getDishByName(String dishName) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Dish.class);
        criteria.add(Restrictions.eq("name", dishName));
        Dish dish = (Dish) criteria.list().get(0);
        if (dish != null) {
            return dish;
        } else {
            throw new RuntimeException("Cant get dish by this dish name! Error!");
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void removeDish(Dish dish) {
        sessionFactory.getCurrentSession().delete(dish);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void remove(int id) {
        Session session = sessionFactory.getCurrentSession();
        Dish dish = session.load(Dish.class, id);
        session.delete(dish);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Dish getDishById(int dishId) {
        Session session = sessionFactory.getCurrentSession();
        Dish dish = (Dish) session.load(Dish.class, dishId);
        if (dish != null) {
            return dish;
        } else {
            throw new RuntimeException("Cant get dish by this id! Error");
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Dish> search(String pattern) {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT d FROM Dish d WHERE d.name LIKE :pattern", Dish.class);
        query.setParameter("pattern", "%" + pattern + "%");
        return (List<Dish>) query.getResultList();
    }

    private void createDish(String name, String dishCategory, double price, int weight, String photoLink, Dish dish) {
        setDishCategory(dishCategory, dish);
        dish.setName(name);
        dish.setPrice(price);
        dish.setWeight(weight);
        dish.setPhotoLink(photoLink);
    }

    private void setDishCategory(String dishCategory, Dish dish) {
        for (DishCategory category : DishCategory.values()) {
            if (dishCategory.equals(category.toString())) {
                dish.setDishCategory(category);
            }
        }
    }
}
