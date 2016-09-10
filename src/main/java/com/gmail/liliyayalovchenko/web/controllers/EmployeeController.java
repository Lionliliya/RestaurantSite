package com.gmail.liliyayalovchenko.web.controllers;

import com.gmail.liliyayalovchenko.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public ModelAndView employees() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("employees", employeeService.getAllWaiters())
                .setViewName("employees");
        return modelAndView;
    }

}
