package com.gmail.liliyayalovchenko.service;


import com.gmail.liliyayalovchenko.dao.EmployeeDAO;
import com.gmail.liliyayalovchenko.domain.Employee;
import com.gmail.liliyayalovchenko.web.exeptions.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;

    @Transactional
    public List<Employee> getAllEmployees() {
        return employeeDAO.findAll();
    }

    @Transactional
    public Employee getEmployeeByName(String name, String surname) throws EmployeeNotFoundException {
       return employeeDAO.findByName(name, surname);
    }

    @Transactional
    public List<Employee> getAllWaiters() {
        return employeeDAO.getAllWaiters();
    }

    @Transactional
    public Employee getEmployeeById(int id) {
        return employeeDAO.getById(id);
    }

    @Transactional
    public List<Employee> getEmployeeByFirstName(String employeeName) {
        return employeeDAO.getByFirstName(employeeName);
    }

    @Transactional
    public List<Employee> getEmployeeBySecondName(String employeeSecondName) {
        return employeeDAO.getBySecondName(employeeSecondName);
    }

    @Transactional
    public void remove(int id) {
        employeeDAO.removeEmployee(id);
    }
}
