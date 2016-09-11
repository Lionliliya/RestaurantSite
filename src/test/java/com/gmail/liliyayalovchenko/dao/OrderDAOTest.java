package com.gmail.liliyayalovchenko.dao;

import com.gmail.liliyayalovchenko.domain.*;
import com.gmail.liliyayalovchenko.web.configuration.WebConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class OrderDAOTest {

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    DishDAO dishDAO;

    private SessionFactory sessionFactory;

    private Session session;

    private Transaction transaction;

    @Before
    public void before() {
        sessionFactory = createSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void testGetOpenOrClosedOrder() throws Exception {
        Menu menu = createMenu("Summer");

        persistMenu(session, transaction, menu);

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Melon"));
        ingredients.add(new Ingredient("Mint"));

        for (Ingredient ingredient : ingredients) {
            persistIngredient(session, transaction, ingredient);
        }

        List<Dish> dishList = populateDishList(ingredients, menu, DishCategory.DRINKS);
        for (Dish dish : dishList) {
            persistDish(session, transaction, dish);
        }

        Employee employee = createEmployee();
        persistEmployee(session, transaction, employee);

        Order order = createOrder(dishList, employee);

        persistOrder(session, transaction, order);
        session.close();

        List<Order> orderFromSource = orderDAO.getOpenOrClosedOrder(OrderStatus.opened);

        assertNotNull(orderFromSource);
        assertEquals(order.getStatus(), orderFromSource.get(0).getStatus());

        removeTestObjects(sessionFactory, dishList, menu, ingredients, order, employee);
    }

    private Order createOrder(List<Dish> dishList, Employee employee) {
        Order order = new Order();
        order.setOrderNumber(orderDAO.getLastOrder() + 1);
        order.setOrderDate(new Date());
        order.setTableNumber(7);
        order.setStatus(OrderStatus.opened);
        order.setEmployeeId(employee);
        order.setDishList(dishList);
        return order;
    }

    private void removeTestObjects(SessionFactory sessionFactory, List<Dish> dishList,
                                   Menu menu, List<Ingredient> ingredients, Order order, Employee employee) throws Exception {
        Session session  = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        dishList.forEach(session::remove);

        ingredients.forEach(session::remove);

        session.remove(menu);

        session.remove(order);

        session.remove(employee);

        transaction.commit();
        session.close();
    }

    private void persistEmployee(Session session, Transaction transaction, Employee employee) {
        transaction.begin();
        session.save(employee);
        transaction.commit();
    }

    private void persistOrder(Session session, Transaction transaction, Order order) {
        transaction.begin();
        session.save(order);
        transaction.commit();
    }

    private void persistDish(Session session, Transaction transaction, Dish dish) {
        transaction.begin();
        session.save(dish);
        transaction.commit();
    }

    private void persistMenu(Session session, Transaction transaction, Menu menu) {
        session.save(menu);
        transaction.commit();
    }

    private void persistIngredient(Session session, Transaction transaction, Ingredient ingredient) {
        transaction.begin();
        session.save(ingredient);
        transaction.commit();
    }

    private List<Dish> populateDishList(List<Ingredient> ingredients, Menu menu, DishCategory category) {
        List<Dish> dishList = new ArrayList<>();
        dishList.add(createDish(ingredients, menu, category, "Melon fresh"));
        return dishList;
    }

    private SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Ingredient.class)
                .addAnnotatedClass(Menu.class)
                .addAnnotatedClass(Dish.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(DishCategory.class)
                .addAnnotatedClass(Position.class)
                .addAnnotatedClass(Waiter.class);
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

    private Employee createEmployee() {
        Employee employee = new Waiter();
        employee.setFirstName("FirstName");
        employee.setSecondName("SecondName");
        employee.setPhone("phone");
        employee.setEmplDate(new Date());
        employee.setPosition(Position.WAITER);
        employee.setSalary(4000);
        return employee;
    }
}