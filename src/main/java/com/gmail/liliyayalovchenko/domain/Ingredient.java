package com.gmail.liliyayalovchenko.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmail.liliyayalovchenko.jsonViews.Views;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "INGREDIENT")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ingredient {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    @JsonView(Views.Internal.class)
    private int id;

    @Column(name = "name")
    @JsonView(Views.Internal.class)
    private String name;

    @ManyToMany(mappedBy = "ingredients")
    private List<Dish> dishList;

    public Ingredient() {}

    public Ingredient(String name) {
        this.name = name;
    }

    public Ingredient(String name, List<Dish> dishList) {
        this.name = name;
        this.dishList = dishList;
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient)) return false;

        Ingredient that = (Ingredient) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
