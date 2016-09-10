package com.gmail.liliyayalovchenko.dao.hibernate;

import com.gmail.liliyayalovchenko.dao.EmployeeDAO;
import com.gmail.liliyayalovchenko.domain.Employee;
import com.gmail.liliyayalovchenko.domain.Position;
import com.gmail.liliyayalovchenko.web.exeptions.EmployeeNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void save(Employee employee) {
        sessionFactory.getCurrentSession().save(employee);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void save(int id, String secondName, String firstName, String dateOfEmpl, String phone, String position, int salary, String photoLink) throws ParseException {
        Employee employee = sessionFactory.getCurrentSession().load(Employee.class, id);
        employee.setFirstName(firstName);
        employee.setSecondName(secondName);
        employee.setEmplDate(new SimpleDateFormat("dd-MM-yyyy").parse(dateOfEmpl));
        employee.setPhone(phone);
        for (Position position1 : Position.values()) {
            if (position.equals(position1.toString())) {
                employee.setPosition(position1);
            }
        }
        employee.setSalary(salary);
        employee.setPhotoLink(photoLink);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Employee getById(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Employee employee = currentSession.load(Employee.class, id);
        if (employee == null) {
            throw new RuntimeException("Cant get Employee by this id. Wrong id!");
        } else {
            return employee;
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Employee> findAll() {
        return sessionFactory.getCurrentSession().createQuery("select e from Employee e").list();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void removeEmployee(String firstName, String secondName) {
        Session session = sessionFactory.getCurrentSession();
        Employee employee = (Employee) session.createQuery("select e from Employee e where e.firstName = :var1 and e.secondName= :var2")
                .setParameter("var1", firstName)
                .uniqueResult();
        if (employee != null) {
            session.delete(employee);
        } else {
            throw new RuntimeException("Cant find employee by this name. Error!");
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void removeEmployee(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(session.load(Employee.class, id));
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Employee findByName(String firstName, String secondName) throws EmployeeNotFoundException {
        Session session = sessionFactory.getCurrentSession();
        Employee employee = (Employee) session.createQuery("select e from Employee e where e.firstName = :var1 and e.secondName= :var2")
                .setParameter("var1", firstName)
                .setParameter("var2", secondName)
                .uniqueResult();

        if (employee == null) {
            throw new EmployeeNotFoundException(firstName, secondName);
        } else {
            return employee;
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Employee> getAllWaiters() {
        return sessionFactory.getCurrentSession()
                .createQuery("select e from Employee e where e.position = :var1 or e.position = :var2")
                .setParameter("var1", Position.WAITRESS)
                .setParameter("var2", Position.WAITER)
                .list();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Employee> getByFirstName(String employeeName) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select  e from Employee e where e.firstName =:employeeName");
        query.setParameter("employeeName", employeeName);
        return query.list();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Employee> getBySecondName(String employeeSecondName) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select  e from Employee e where e.secondName =:employeeSecondName");
        query.setParameter("employeeSecondName", employeeSecondName);
        return query.list();
    }
}
