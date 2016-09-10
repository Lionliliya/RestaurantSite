package com.gmail.liliyayalovchenko.web.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.liliyayalovchenko.domain.Employee;
import com.gmail.liliyayalovchenko.jsonViews.Views;
import com.gmail.liliyayalovchenko.service.EmployeeService;
import com.gmail.liliyayalovchenko.web.exeptions.EmployeeNotFoundException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class EmployeeAPI {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;


    /**
     * Method that gets the employee by id
     * If employee is a waiter or waitress, he's orders also serialized
     *
     * @author Liliya Yalovchenko
     * **/
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
    @JsonView(Views.Internal.class)
    public ResponseEntity<Employee> employeeById(@PathVariable int id) throws EmployeeNotFoundException {
        Employee employeeById;
        try {
            employeeById = employeeService.getEmployeeById(id);
            objectMapper.setConfig(objectMapper.getSerializationConfig().withView(Views.Internal.class));
            objectMapper.setVisibility(objectMapper.getVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.NONE));
        } catch (ObjectNotFoundException ex) {
            throw new EmployeeNotFoundException(id);
        }

        return new ResponseEntity<>(employeeById, HttpStatus.OK);
    }

    /**
     * Method that gets the employee
     * by first name and second name
     * If employee is a waiter or waitress, he's orders also serialized
     *
     * @author Liliya Yalovchenko
     * **/
    @RequestMapping(value = "/employee/{employeeName}/{employeeSecondName}", method = RequestMethod.GET)
    @JsonView(Views.Internal.class)
    public ResponseEntity<Employee> employeeByFullName(@PathVariable String employeeName,
                                       @PathVariable String employeeSecondName) throws EmployeeNotFoundException {
        Employee employee;
        try {
            employee = employeeService.getEmployeeByName(employeeName, employeeSecondName);
        } catch (ObjectNotFoundException ex) {
            throw new EmployeeNotFoundException(employeeName, employeeSecondName);
        }
        objectMapper.setConfig(objectMapper.getSerializationConfig().withView(Views.Internal.class));
        objectMapper.setVisibility(objectMapper.getVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.NONE));
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    /**
     * Method that gets the employees list
     * by first name
     * If employee is a waiter or waitress, he's orders also serialized
     *
     * @author Liliya Yalovchenko
     * **/
    @RequestMapping(value = "/employee/firstName/{employeeName}", method = RequestMethod.GET)
    @JsonView(Views.Internal.class)
    public ResponseEntity<List<Employee>> employeeByFirstName(@PathVariable String employeeName) throws EmployeeNotFoundException {
        List<Employee> employeesByName = employeeService.getEmployeeByFirstName(employeeName);
        objectMapper.setConfig(objectMapper.getSerializationConfig().withView(Views.Internal.class));
        objectMapper.setVisibility(objectMapper.getVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.NONE));
        if(employeesByName.isEmpty()){
            throw new EmployeeNotFoundException(employeeName);
        }
        return new ResponseEntity<>(employeesByName, HttpStatus.OK);
    }

    /**
     * Method that gets the employees list
     * by second name
     * If employee is a waiter or waitress, he's orders also serialized
     *
     * @author Liliya Yalovchenko
     * **/
    @RequestMapping(value = "/employee/secondName/{employeeSecondName}", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> employeeBySecondName(@PathVariable String employeeSecondName) throws EmployeeNotFoundException {
        List<Employee> employeeBySecondName = employeeService.getEmployeeBySecondName(employeeSecondName);
        objectMapper.setConfig(objectMapper.getSerializationConfig().withView(Views.Internal.class));
        objectMapper.setVisibility(objectMapper.getVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.NONE));
        if(employeeBySecondName.isEmpty()){
            throw new EmployeeNotFoundException(employeeSecondName);
        }
        return new ResponseEntity<>(employeeBySecondName, HttpStatus.OK);
    }


    /**
     * Method that gets a list of all employees
     * (only the first names and second names)
     *
     * @author Liliya Yalovchenko
     * **/
    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    @JsonView(Views.Public.class)
    public ResponseEntity<List<Employee>> listAllUsers() throws EmployeeNotFoundException {
        List<Employee> employees = employeeService.getAllEmployees();
        objectMapper.setConfig(objectMapper.getSerializationConfig().withView(Views.Public.class));
        if(employees.isEmpty()){
           throw new EmployeeNotFoundException();
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
}
