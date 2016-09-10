package com.gmail.liliyayalovchenko.dao.hibernate;

import com.gmail.liliyayalovchenko.dao.OrderDAO;
import com.gmail.liliyayalovchenko.domain.Dish;
import com.gmail.liliyayalovchenko.domain.Order;
import com.gmail.liliyayalovchenko.domain.OrderStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void save(Order order) {
        sessionFactory.getCurrentSession().save(order);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Order> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("select o from Order o")
                .list();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Order> getOrderByEmployee(String name) {
        return sessionFactory.getCurrentSession()
                .createQuery("select o from Order o where o.employeeId.secondName =:var")
                .setParameter("var", name)
                .list();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Order> getOrderByDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        Date stDate = sdf.parse(date);
        Date edDate = plusOneDay(sdf, date);

        return sessionFactory.getCurrentSession()
                .createQuery("select o from Order o where  o.orderDate between :stDate and :edDate")
                .setParameter("stDate", stDate)
                .setParameter("edDate", edDate).list();
    }

    private Date plusOneDay(SimpleDateFormat sdf, String date) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date));
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Order> getOrderByTable(int tableNumber) {
        return sessionFactory.getCurrentSession()
                .createQuery("select o from Order o where o.tableNumber =:tablNumb")
                .setParameter("tablNumb", tableNumber)
                .list();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addDishToOpenOrder(Dish dish, int orderNumber) {
        Session session = sessionFactory.getCurrentSession();
        Order order = (Order) session.createQuery("select o from Order o where o.orderNumber =:orderNumber")
                .setParameter("orderNumber", orderNumber)
                .list().get(0);
        if (order == null) {
            throw new RuntimeException("Cant get order by this order number! Wrong order number");
        } else {
            order.addDishToOrder(dish);
            session.update(order);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteOrder(int orderNumber) {
        Session session = sessionFactory.getCurrentSession();
        Order order = (Order) session.createQuery("select o from Order o where o.orderNumber =:orderNumber")
                .setParameter("orderNumber", orderNumber)
                .list().get(0);
        if (order == null) {
            throw new RuntimeException("Cant get order by this order number! Wrong order number");
        } else {
            session.delete(order);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void changeOrderStatus(int orderNumber) {
        Session session = sessionFactory.getCurrentSession();
        Order order = (Order) session.createQuery("select o from Order o where o.orderNumber =:orderNumber")
                .setParameter("orderNumber", orderNumber)
                .list().get(0);
        if (order != null) {
            order.setStatus(OrderStatus.closed);
            session.update(order);

        } else {
            throw new RuntimeException("Cant get order by this order number! Wrong order number");
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Order> getOpenOrClosedOrder(OrderStatus orderStatus) {
        return sessionFactory.getCurrentSession()
                .createQuery("select o from Order o where o.orderStatus =:orderStatus")
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

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public int getLastOrder() {
        return (int) sessionFactory.getCurrentSession()
                .createQuery("select max(o.orderNumber) from Order o")
                .uniqueResult();
    }
}
