package com.gmail.liliyayalovchenko.web.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Employee Not Found By This Id")
public class EmployeeNotFoundException extends Exception {

    public EmployeeNotFoundException() {
        super("List of employee is empty");
    }

    public EmployeeNotFoundException(int id) {
        super("EmployeeNotFoundException with id=" + id);
    }

    public EmployeeNotFoundException(String firstName, String secondName) {
        super("EmployeeNotFoundException with firstName=" + firstName + " secondName=" + secondName);
    }

    public EmployeeNotFoundException(String property) {
        super("EmployeeNotFoundException with property=" + property);
    }

}
