package com.gmail.liliyayalovchenko.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmail.liliyayalovchenko.jsonViews.Views;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Proxy(lazy = false)
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Waiter extends Employee {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    //@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonView(Views.Internal.class)
    private List<Order> orderList;

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Waiter {\n").append(" id = ").append(getId()).append("\n").
                append(" name = ").append(getFirstName()).append(" ").append(getSecondName()).append("\n").
                append(" orders = {\n");
        orderList.forEach(order -> sb.append("   ").append(order).append(",\n"));
        sb.append("}\n").append(" }");
        return sb.toString();
    }
}
