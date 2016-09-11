package com.gmail.liliyayalovchenko.dao.hibernate;

import com.gmail.liliyayalovchenko.dao.OrderDAO;
import com.gmail.liliyayalovchenko.domain.Order;
import com.gmail.liliyayalovchenko.domain.OrderStatus;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Order> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("select o from Order o")
                .list();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Order> getOpenOrClosedOrder(OrderStatus orderStatus) {
        return sessionFactory.getCurrentSession()
                .createQuery("select o from Order o where o.status =:orderStatus")
                .setParameter("orderStatus", orderStatus)
                .list();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Order getOrderById(int i) {
        Order order = sessionFactory.getCurrentSession().load(Order.class, i);
        if (order != null) {
            return order;
        } else {
            throw new RuntimeException("Cant get order by this id! Error!");
        }
    }

}
