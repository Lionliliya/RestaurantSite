package com.gmail.liliyayalovchenko.dao;

import com.gmail.liliyayalovchenko.domain.Employee;
import com.gmail.liliyayalovchenko.domain.Position;
import com.gmail.liliyayalovchenko.web.configuration.WebConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class EmployeeDAOTest {

    @Autowired
    EmployeeDAO employeeDAO;

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
    public void testGetById() throws Exception {
        Employee employee = createEmployee();
        persistTestObject(employee);
        Employee employeeFromSource = employeeDAO.getById(employee.getId());

        assertNotNull(employeeFromSource);
        assertEquals(employee.getFirstName(), employeeFromSource.getFirstName());
        assertEquals(employee.getPosition(), employeeFromSource.getPosition());
        assertEquals(employee.getId(), employeeFromSource.getId());

        removeTestObjects(sessionFactory, employee);

    }

    @Test
    @Transactional
    public void testGetByFirstName() throws Exception {
        Employee employee = createEmployee();
        persistTestObject(employee);

        Employee employeeFromSource = employeeDAO.getByFirstName((employee.getFirstName())).get(0);

        assertNotNull(employeeFromSource);
        assertEquals(employee.getFirstName(), employeeFromSource.getFirstName());
        assertEquals(employee.getPosition(), employeeFromSource.getPosition());
        assertEquals(employee.getId(), employeeFromSource.getId());

        removeTestObjects(sessionFactory, employee);
    }

    private void persistTestObject(Employee employee) throws Exception {
        session.save(employee);
        transaction.commit();
        session.close();
    }

    private void removeTestObjects(SessionFactory sessionFactory, Employee employee) throws Exception {
        Session session  = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(employee);
        transaction.commit();
        session.close();
    }

    private SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Employee.class);
        configuration.setProperty("hibernate.dialect",
                "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty("hibernate.connection.driver_class",
                "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/RestaurantCRM");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "123_lion_123");
        return configuration.buildSessionFactory();
    }

    private Employee createEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("FirstName");
        employee.setSecondName("SecondName");
        employee.setPhone("phone");
        employee.setEmplDate(new Date());
        employee.setPosition(Position.MANAGER);
        employee.setSalary(4000);
        return employee;
    }

}