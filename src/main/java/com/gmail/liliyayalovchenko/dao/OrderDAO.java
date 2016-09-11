package com.gmail.liliyayalovchenko.dao;

import com.gmail.liliyayalovchenko.domain.Order;
import com.gmail.liliyayalovchenko.domain.OrderStatus;

import java.text.ParseException;
import java.util.List;

public interface OrderDAO {

    void save(Order order);

    List<Order> findAll();

    List<Order> getOpenOrClosedOrder(OrderStatus orderStatus);

    Order getOrderById(int i);

    List<Order> getOrderByEmployee(String name);

    List<Order> getOrderByDate(String date) throws ParseException;

    List<Order> getOrderByTable(int tableNumber);
}
