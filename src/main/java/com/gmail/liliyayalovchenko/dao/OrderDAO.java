package com.gmail.liliyayalovchenko.dao;

import com.gmail.liliyayalovchenko.domain.Order;
import com.gmail.liliyayalovchenko.domain.OrderStatus;

import java.util.List;

public interface OrderDAO {

    List<Order> findAll();

    List<Order> getOpenOrClosedOrder(OrderStatus orderStatus);

    Order getOrderById(int i);

}
