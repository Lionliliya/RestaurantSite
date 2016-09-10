package com.gmail.liliyayalovchenko.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.gmail.liliyayalovchenko.jsonViews.Views;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "MENU")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(ignoreUnknown = false)
@Proxy(lazy = false)
public class Menu {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    @JsonView(Views.Internal.class)
    private int id;

    @Column(name = "name")
    @JsonView(Views.Public.class)
    private String name;

    @OneToMany(mappedBy="menu", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @LazyCollection(LazyCollectionOption.FALSE)
    //@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonView(Views.Internal.class)
    private List<Dish> dishList;

    public Menu(List<Dish> dishList, String name) {
        this.dishList = dishList;
        this.name = name;
    }

    public Menu() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Dish> getDishList() {
        return dishList;
    }

    public void setDishList(List<Dish> dishList) {
        this.dishList = dishList;
    }



    @Override
    public String toString() {
       return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dishList=" + printDishList() +
                '}';
    }

    private String printDishList() {
        StringBuilder dishPrint = new StringBuilder();
        dishPrint.append("[ ");
        for (Dish dish : dishList) {
            dishPrint.append(dish.getName()).append(", ");
        }
        dishPrint.append(" ]");
        return dishPrint.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Menu)) return false;

        Menu menu = (Menu) o;

        if (name != null ? !name.equals(menu.name) : menu.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public void removeDishFromMenu(Dish dish) {
        dishList.remove(dish);
        dish.setMenu(null);
    }

    public void removeAllDishes() {
        for (Dish dish : dishList) {
            dish.setMenu(null);
        }
        dishList.clear();

    }


    public void addDishToMenu(Dish dish) {
        if (!dishList.contains(dish)) {
            dishList.add(dish);
        }
        dish.setMenu(this);
    }



}
