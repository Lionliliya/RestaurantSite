package com.gmail.liliyayalovchenko.web.controllers;

import com.gmail.liliyayalovchenko.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    public ModelAndView menus() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("menus");
        modelAndView.addObject("menus", menuService.getAllMenus());
        return modelAndView;
    }
}
