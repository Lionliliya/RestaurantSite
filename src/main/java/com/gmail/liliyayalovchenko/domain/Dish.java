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
@Table(name = "DISH")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(ignoreUnknown = false)
@Proxy(lazy = false)
public class Dish {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    @JsonView(Views.Public.class)
    private int id;

    @Column(name = "name")
    @JsonView(Views.Public.class)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    @JsonView(Views.Public.class)
    private DishCategory dishCategory;

    @Column(name = "price")
    @JsonView(Views.Public.class)
    private double price;

    @Column(name = "weight")
    @JsonView(Views.Public.class)
    private int weight;

    @ManyToMany()
    @JoinTable(name = "DISH_INGREDIENTS",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    //@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonView(Views.Internal.class)
    private List<Ingredient> ingredients;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = true)
    private Menu menu;

    @Column(name = "photo")
    private String photoLink;

    public Dish(String name, DishCategory dishCategory, double price, int weight, List<Ingredient> ingredients, Menu menu, String photoLink) {
        this.name = name;
        this.dishCategory = dishCategory;
        this.price = price;
        this.weight = weight;
        this.ingredients = ingredients;
        this.menu = menu;
        this.photoLink = photoLink;
    }

    public Dish(Menu menu, List<Ingredient> ingredients, int weight, double price, DishCategory dishCategory, String name) {
        this.menu = menu;
        this.ingredients = ingredients;
        this.weight = weight;
        this.price = price;
        this.dishCategory = dishCategory;
        this.name = name;
    }

    public Dish() {}

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDishCategory(DishCategory dishCategory) {
        this.dishCategory = dishCategory;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public DishCategory getDishCategory() {
        return dishCategory;
    }

    public double getPrice() {
        return price;
    }

    public int getWeight() {
        return weight;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;

    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dish)) return false;

        Dish dish = (Dish) o;

        if (Double.compare(dish.price, price) != 0) return false;
        if (weight != dish.weight) return false;
        if (dishCategory != dish.dishCategory) return false;
        if (ingredients != null ? !ingredients.equals(dish.ingredients) : dish.ingredients != null) return false;
        if (name != null ? !name.equals(dish.name) : dish.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + (dishCategory != null ? dishCategory.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + weight;
        result = 31 * result + (ingredients != null ? ingredients.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
       return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dishCategory=" + dishCategory +
                ", price=" + price +
                ", weight=" + weight +
                ", ingredients=" + printIngredientNames() +
                ", menu=" + menu.getName() +
                '}';
    }

    private String printIngredientNames() {
        StringBuilder inrgredientsPrint = new StringBuilder();
        inrgredientsPrint.append("[");
        for (Ingredient ingredient : ingredients) {
            inrgredientsPrint.append(ingredient.getName()).append(", ");
        }
        inrgredientsPrint.append(" ]");
        return inrgredientsPrint.toString();
    }


}
