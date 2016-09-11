package com.gmail.liliyayalovchenko.dao;

import com.gmail.liliyayalovchenko.domain.Dish;
import com.gmail.liliyayalovchenko.domain.DishCategory;
import com.gmail.liliyayalovchenko.domain.Ingredient;
import com.gmail.liliyayalovchenko.domain.Menu;
import com.gmail.liliyayalovchenko.service.DishService;
import com.gmail.liliyayalovchenko.web.configuration.WebConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class DishDAOTest {

    @Autowired
    private DishService dishService;


    @Test
     public void shouldGetByName() throws Exception {
         SessionFactory   sessionFactory = createSessionFactory();
         Session   session = sessionFactory.openSession();
         Transaction transaction = session.beginTransaction();

        Menu menu = createMenu("Summer");
        session.save(menu);
        transaction.commit();

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Melon"));

        for (Ingredient ingredient : ingredients) {
            transaction.begin();
            session.save(ingredient);
            transaction.commit();
        }

        Dish dish = createDish(ingredients, menu, DishCategory.DRINKS, "Melon fresh");
        transaction.begin();
        session.save(dish);
        transaction.commit();
        session.close();

        Dish dishFromSource = dishService.getDishByName(dish.getName());

        assertNotNull(dishFromSource);
        assertEquals(dish.getName(), dishFromSource.getName());
        assertEquals(dish.getMenu().getName(), dishFromSource.getMenu().getName());

        removeTestObjects(sessionFactory, menu, ingredients, dish);

     }



    private void removeTestObjects(SessionFactory sessionFactory, Menu menu, List<Ingredient> ingredients, Dish dish) throws Exception {
        Session session  = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.remove(dish);
        ingredients.forEach(session::remove);
        session.remove(menu);

        transaction.commit();
        session.close();
    }


    private SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Dish.class)
                .addAnnotatedClass(Menu.class)
                .addAnnotatedClass(Ingredient.class)
                .addAnnotatedClass(DishCategory.class);
        configuration.setProperty("hibernate.dialect",
                "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty("hibernate.connection.driver_class",
                "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/RestaurantCRM");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "123_lion_123");
        return configuration.buildSessionFactory();
    }

    private Dish createDish(List<Ingredient> ingredients, Menu menu, DishCategory category, String name) {
        Dish dish = new Dish();
        dish.setName(name);
        dish.setMenu(menu);
        dish.setDishCategory(category);
        dish.setWeight(250);
        dish.setPrice(40);
        dish.setIngredients(ingredients);
        return dish;
    }

    private Menu createMenu(String name) {
        Menu menu = new Menu();
        menu.setName(name);
        return menu;
    }
}