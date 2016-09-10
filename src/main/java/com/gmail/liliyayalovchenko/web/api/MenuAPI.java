package com.gmail.liliyayalovchenko.web.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.liliyayalovchenko.domain.Menu;
import com.gmail.liliyayalovchenko.jsonViews.Views;
import com.gmail.liliyayalovchenko.service.MenuService;
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
public class MenuAPI {

    @Autowired
    private MenuService menuService;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Method returns a list of all the menu
     * (the menu names only, without the dishes in them)
     *
     * @author Liliya Yalovchenko
     * **/
    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    @JsonView(Views.Public.class)
    public ResponseEntity<List<Menu>> menu() {
        List<Menu> menuNames;
        try {
            menuNames = menuService.getAllMenus();
        } catch (Exception ex) {
            menuNames = null;
        }
        objectMapper.setConfig(objectMapper.getSerializationConfig().withView(Views.Public.class));
        if(menuNames == null || menuNames.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(menuNames, HttpStatus.OK);
    }

    /**
     * Method that returns a menu by id with
     * a list of dishes in it
     *
     * @author Liliya Yalovchenko
     * **/
    @RequestMapping(value = "/menu/{id}", method = RequestMethod.GET)
    @JsonView(Views.Internal.class)
    public ResponseEntity<Menu> menu(@PathVariable int id) {
        Menu menu;
        try {
            menu = menuService.getMenuById(id);
            objectMapper.setConfig(objectMapper.getSerializationConfig().withView(Views.Internal.class));
            objectMapper.setVisibility(objectMapper.getVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.NONE));
        } catch (Exception ex) {
            menu = null;
        }

        if(menu == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }


    /**
     * Method that returns a menu by name
     * full menu instance(with dish list, name and id)
     *
     * @author Liliya Yalovchenko
     * **/
    @RequestMapping(value = "/menu/name/{name}", method = RequestMethod.GET)
    @JsonView(Views.Internal.class)
    public ResponseEntity<Menu> menu(@PathVariable String name) {
        Menu menu;

        try {
            menu = menuService.getMenuByName(name);
        } catch (Exception ex) {
            menu = null;
        }

        objectMapper.setConfig(objectMapper.getSerializationConfig().withView(Views.Internal.class));
        objectMapper.setVisibility(objectMapper.getVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.NONE));

        if(menu == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

}
