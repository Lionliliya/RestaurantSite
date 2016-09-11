package com.gmail.liliyayalovchenko.dao;

import com.gmail.liliyayalovchenko.domain.Employee;
import com.gmail.liliyayalovchenko.web.exeptions.EmployeeNotFoundException;

import java.util.List;

public interface EmployeeDAO {

    Employee getById(int id);

    List<Employee> getAllWaiters();

    List<Employee> getByFirstName(String employeeName);

    List<Employee> getBySecondName(String employeeSecondName);

    Employee findByName(String firstName, String secondName) throws EmployeeNotFoundException;

    List<Employee> findAll();

}
