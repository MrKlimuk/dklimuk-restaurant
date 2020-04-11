package com.epam.brest.courses.rest;


import com.epam.brest.courses.model.Item;
import com.epam.brest.courses.rest.exception.ItemNotFoundException;
import com.epam.brest.courses.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;

    }

    @GetMapping(value = "/items")
    public final List<Item> items(){

        LOGGER.debug("items()");

        return itemService.findAllItem();
    }

    @GetMapping("/items/{id}")
    public Item findById(@PathVariable Integer id){

        LOGGER.debug("find item by id({})", id);

        return itemService.findItemById(id).orElseThrow(() -> new ItemNotFoundException(id));
    }


   @PostMapping(path = "/items", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> add(@RequestBody Item item) {
        LOGGER.debug("createItem({})", item);
        Integer id = itemService.createItem(item);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping(value = "/items", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updateItem(@RequestBody Item item) {

        LOGGER.debug("updateItem({})", item);
        int result = itemService.updateItem(item);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/items/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deleteItem(@PathVariable Integer id) {

        int result = itemService.deleteItem(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }

}
