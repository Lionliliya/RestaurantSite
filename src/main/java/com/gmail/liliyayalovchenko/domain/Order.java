package com.gmail.liliyayalovchenko.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.gmail.liliyayalovchenko.jsonViews.Views;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ORDER_FROM_MENU")
@JsonIgnoreProperties(ignoreUnknown = false)
@Proxy(lazy = false)
public class Order {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    @JsonView(Views.Public.class)
    private int id;

    @Column(name = "order_num")
    @JsonView(Views.Public.class)
    private int orderNumber;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonView(Views.Private.class)
    private Employee employeeId;

    @Column(name = "table_num")
    @JsonView(Views.Public.class)
    private int tableNumber;

    @Column(name = "order_date")
    @JsonView(Views.Public.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @JsonView(Views.Public.class)
    private OrderStatus status;

    @ManyToMany()
    @JoinTable(name = "ORDER_DISH",
               joinColumns = @JoinColumn(name = "order_id"),
               inverseJoinColumns = @JoinColumn(name = "dish_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonView(Views.Public.class)
    private List<Dish> dishList;

    public Order(int orderNumber, Employee employeeId, int tableNumber, Date orderDate, OrderStatus status, List<Dish> dishList) {
        this.orderNumber = orderNumber;
        this.employeeId = employeeId;
        this.tableNumber = tableNumber;
        this.orderDate = orderDate;
        this.status = status;
        this.dishList = dishList;
    }

    public Order() {}

    public void addDishToOrder(Dish dish) {
        dishList.add(dish);
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setDishList(List<Dish> dishList) {
        this.dishList = dishList;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public int getId() {
        return id;
    }

    public List<Dish> getDishList() {
        return dishList;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (orderNumber != order.orderNumber) return false;
        if (tableNumber != order.tableNumber) return false;
        if (dishList != null ? !dishList.equals(order.dishList) : order.dishList != null) return false;
        if (employeeId != null ? !employeeId.equals(order.employeeId) : order.employeeId != null) return false;
        if (orderDate != null ? !orderDate.equals(order.orderDate) : order.orderDate != null) return false;
        if (status != order.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderNumber;
        result = 31 * result + (employeeId != null ? employeeId.hashCode() : 0);
        result = 31 * result + tableNumber;
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (dishList != null ? dishList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {

        return "Order{" +
                "id=" + id +
                ", orderNumber=" + orderNumber +
                ", employeeId=" + employeeId.getSecondName() + " " + employeeId.getFirstName() +
                ", tableNumber=" + tableNumber +
                ", orderDate=" + orderDate +
                ", status='" + status + '\'' +
                ", dishList=" + printDishNames() +
                '}';
    }

    private String printDishNames() {
        StringBuilder allDishInOrder = new StringBuilder();
        allDishInOrder.append("[ ");

        for (Dish dish : dishList) {
            allDishInOrder.append(dish.getName()).append(", ");

        }

        allDishInOrder.trimToSize();
        allDishInOrder.append(" ]");

        return allDishInOrder.toString();
    }
}
