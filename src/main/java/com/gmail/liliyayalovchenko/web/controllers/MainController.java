package com.gmail.liliyayalovchenko.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class MainController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = "/schema", method = RequestMethod.GET)
    public ModelAndView schema() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("schema");
        return modelAndView;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public ModelAndView contact() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("contact");
        return modelAndView;
    }

}
