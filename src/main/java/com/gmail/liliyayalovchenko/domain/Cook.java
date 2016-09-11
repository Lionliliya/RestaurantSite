package com.gmail.liliyayalovchenko.domain;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;

@Entity
@Proxy(lazy = false)
@DiscriminatorValue(value="Cook")
public class Cook extends Employee {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ReadyMeal> readyMealList;

    public List<ReadyMeal> getReadyMealList() {
        return readyMealList;
    }

    public void setReadyMealList(List<ReadyMeal> readyMealList) {
        this.readyMealList = readyMealList;
    }

    @Override
    public String toString() {
        return "Cook{" +
                "readyMealList=" + printReadyMealList() +
                '}';
    }

    private String printReadyMealList() {
        StringBuilder sb = new StringBuilder();
        for (ReadyMeal readyMeal : readyMealList) {
            sb.append("Ready meal number " + readyMeal.getDishNumber() + "{ \n").append("   dish  = ").append(readyMeal.getDishId().getName()).append("\n  ").
                    append("employee = ").append(readyMeal.getEmployeeId().getFirstName() + " " + readyMeal.getEmployeeId().getSecondName()).
                    append("\n  ").append("  meal date = ").append(readyMeal.getMealDate()).append("\n  ").
                    append("  order = ").append(readyMeal.getOrderId().getOrderNumber()).append("\n  }\n");
        }

        return sb.toString();

    }
}
