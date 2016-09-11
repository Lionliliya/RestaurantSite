package com.gmail.liliyayalovchenko.web.api;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.liliyayalovchenko.domain.Order;
import com.gmail.liliyayalovchenko.jsonViews.Views;
import com.gmail.liliyayalovchenko.service.OrderService;
import com.gmail.liliyayalovchenko.web.exeptions.OrderNotFoundException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class OrderAPI {

    @Autowired
    private OrderService orderService;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * Method returns a list of all the orders
     * with only employee firstName and secondName
     *
     * @author Liliya Yalovchenko
     */
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    @JsonView(Views.Private.class)
    public ResponseEntity<List<Order>> allOrders() {

        List<Order> orders;

        try {
            orders = orderService.getAllOrders();
        } catch (Exception ex) {
            throw new RuntimeException("Internal error occurred. Cant get menus now. " +
                    "Please, contact support");
        }

        objectMapper.setConfig(objectMapper.getSerializationConfig().withView(Views.Private.class));

        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * Method returns the order by id
     * with only employee firstName and secondName
     *
     * @author Liliya Yalovchenko
     */
    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.Private.class)
    public ResponseEntity<Order> orderById(@PathVariable int id) throws OrderNotFoundException {

        Order order;

        try {
            order = orderService.getOrderById(id);
            objectMapper.setConfig(objectMapper.getSerializationConfig()
                    .withView(Views.Private.class));
        } catch (ObjectNotFoundException ex) {
            throw new OrderNotFoundException(id);
        }

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    /**
     * Method returns a list of all the orders
     * with only employee firstName and secondName
     *
     * @author Liliya Yalovchenko
     */
    @RequestMapping(value = "/order/status/{status}", method = RequestMethod.GET)
    @JsonView(Views.Private.class)
    public ResponseEntity<List<Order>> allOpenOrders(@PathVariable String status) throws OrderNotFoundException {
        boolean isOpen;

        if (status.equals("opened")) {
            isOpen = true;
        } else if (status.equals("closed")) {
            isOpen = false;
        } else {
            throw new OrderNotFoundException(status);
        }

        List<Order> orders;

        try {
            orders = isOpen ? orderService.getAllOpenOrders() : orderService.getAllClosedOrders();

        } catch (Exception ex) {
            throw new OrderNotFoundException(status);
        }

        objectMapper.setConfig(objectMapper.getSerializationConfig()
                .withView(Views.Private.class));

        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
