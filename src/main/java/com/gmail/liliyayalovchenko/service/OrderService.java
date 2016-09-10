package com.gmail.liliyayalovchenko.service;

import com.gmail.liliyayalovchenko.dao.OrderDAO;
import com.gmail.liliyayalovchenko.domain.Order;
import com.gmail.liliyayalovchenko.domain.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderService {

    @Autowired
    private OrderDAO orderDAO;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Transactional
    public List<Order> getAllOrders() {
        LOGGER.info("Try to get all orders");
        List<Order> orders = orderDAO.findAll();
        LOGGER.info("All orders are got");
        return orders;
    }

    @Transactional
    public List<Order> getAllOpenOrders() {
        LOGGER.info("Try to get all open orders");
        List<Order> orders = orderDAO.getOpenOrClosedOrder(OrderStatus.opened);
        LOGGER.info("All opened orders are got");
        return orders;
    }

    @Transactional
    public List<Order> getAllClosedOrders() {
        LOGGER.info("Try to get all closed orders");
        List<Order> orders = orderDAO.getOpenOrClosedOrder(OrderStatus.closed);
        LOGGER.info("All closed orders are got");
        return orders;
    }

    @Transactional
    public Order getOrderById(int id) {
        LOGGER.info("Try to get order by id");
        Order order = orderDAO.getOrderById(id);
        LOGGER.info("Order by id " + id + " is got");
        return order;
    }

    @Transactional
    public List<Order> getOrderByEmployee(String name) {
        return orderDAO.getOrderByEmployee(name);
    }

    @Transactional
    public List<Order> getOrderByDate(String date) {
        try {
            return orderDAO.getOrderByDate(date);
        } catch (ParseException e) {
            LOGGER.error("Input error  for date " + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }

    @Transactional
    public List<Order> getOrderByTable(int tableNumber) {
        return orderDAO.getOrderByTable(tableNumber);
    }


}
