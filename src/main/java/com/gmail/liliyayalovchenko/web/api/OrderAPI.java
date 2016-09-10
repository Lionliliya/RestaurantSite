package com.gmail.liliyayalovchenko.web.api;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.liliyayalovchenko.domain.Order;
import com.gmail.liliyayalovchenko.jsonViews.Views;
import com.gmail.liliyayalovchenko.service.OrderService;
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
     **/
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    @JsonView(Views.Private.class)
    public ResponseEntity<List<Order>> allOrders() {
        List<Order> orders;
        try {
            orders = orderService.getAllOrders();
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        objectMapper.setConfig(objectMapper.getSerializationConfig().withView(Views.Private.class));
        if(orders.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


    /**
     * Method returns a list of all the orders
     * with only employee firstName and secondName
     *
     * @author Liliya Yalovchenko
     **/
    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(Views.Private.class)
    public ResponseEntity<Order> orderById(@PathVariable int id) {
        Order order;
        try {
            order = orderService.getOrderById(id);
            objectMapper.setConfig(objectMapper.getSerializationConfig().withView(Views.Private.class));
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (order == null) {
            System.out.println("Order with id " + id + " has no content");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    /**
     * Method returns a list of all the orders
     * with only employee firstName and secondName
     *
     * @author Liliya Yalovchenko
     **/
    @RequestMapping(value = "/order/status/opened", method = RequestMethod.GET)
    @JsonView(Views.Private.class)
    public ResponseEntity<List<Order>> allOpenOrders() {
        List<Order> orders;

        try {
            orders = orderService.getAllOpenOrders();
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        objectMapper.setConfig(objectMapper.getSerializationConfig().withView(Views.Private.class));

        if(orders.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * Method returns a list of all the orders
     * with only employee firstName and secondName
     *
     * @author Liliya Yalovchenko
     **/
    @RequestMapping(value = "/order/status/closed", method = RequestMethod.GET)
    @JsonView(Views.Private.class)
    public ResponseEntity<List<Order>> allClosedOrders() {
        List<Order> orders;

        try {
            orders = orderService.getAllClosedOrders();
            objectMapper.setConfig(objectMapper.getSerializationConfig().withView(Views.Private.class));
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(orders.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
