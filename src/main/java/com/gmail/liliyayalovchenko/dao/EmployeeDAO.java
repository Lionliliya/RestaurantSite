package com.gmail.liliyayalovchenko.dao;

import com.gmail.liliyayalovchenko.domain.Employee;
import com.gmail.liliyayalovchenko.web.exeptions.EmployeeNotFoundException;

import java.text.ParseException;
import java.util.List;

public interface EmployeeDAO {

    void save(Employee employee);

    void save(int id, String secondName, String firstName, String dateOfEmpl,
              String phone, String position, int salary, String photoLink) throws ParseException;

    Employee getById(int id);

    List<Employee> getAllWaiters();

    List<Employee> getByFirstName(String employeeName);

    List<Employee> getBySecondName(String employeeSecondName);

    Employee findByName(String firstName, String secondName) throws EmployeeNotFoundException;

    List<Employee> findAll();

    void removeEmployee(String firstName, String secondName);

    void removeEmployee(int id);






}
