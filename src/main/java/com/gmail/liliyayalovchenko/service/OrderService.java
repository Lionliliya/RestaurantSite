package com.gmail.liliyayalovchenko.service;

import com.gmail.liliyayalovchenko.dao.OrderDAO;
import com.gmail.liliyayalovchenko.domain.Order;
import com.gmail.liliyayalovchenko.domain.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class OrderService {

    @Autowired
    private OrderDAO orderDAO;

    @Transactional
    public List<Order> getAllOrders() {
        return orderDAO.findAll();
    }

    @Transactional
    public List<Order> getAllOpenOrders() {
        return orderDAO.getOpenOrClosedOrder(OrderStatus.opened);
    }

    @Transactional
    public List<Order> getAllClosedOrders() {
        return orderDAO.getOpenOrClosedOrder(OrderStatus.closed);
    }

    @Transactional
    public Order getOrderById(int id) {
        return orderDAO.getOrderById(id);
    }
}
