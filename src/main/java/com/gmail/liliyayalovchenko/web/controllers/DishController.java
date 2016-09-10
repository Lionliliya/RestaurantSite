package com.gmail.liliyayalovchenko.web.controllers;

import com.gmail.liliyayalovchenko.domain.Dish;
import com.gmail.liliyayalovchenko.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class DishController {

    @Autowired
    private DishService dishService;

    @RequestMapping(value = "/dish", method = RequestMethod.GET)
    public ModelAndView dish(@RequestParam("dishName") String dishName) {

        Dish dish = dishService.getDishByName(dishName);
        ModelAndView model = new ModelAndView();
        model.addObject("dish", dish);
        model.setViewName("dish");
        return model;
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ModelAndView search(@RequestParam(value = "pattern") String pattern) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("dishes", dishService.search(pattern));
        modelAndView.setViewName("search");
        return modelAndView;
    }
}
