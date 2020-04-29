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

/**
 * Rest controller for item.
 */
@RestController
public class ItemController {

    /**
     * Default logger for item class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    /**
     * Object for communication with the service level
     */
    private final ItemService itemService;

    /**
     * Constructor accepts service layer object.
     * @param itemService
     */
    public ItemController(ItemService itemService) {
        this.itemService = itemService;

    }

    /**
     * Find all items.
     *
     * @return  list with all found items.
     */
    @GetMapping(value = "/items")
    public final List<Item> items(){

        LOGGER.debug("items()");

        return itemService.findAllItem();
    }

    /**
     * Find item by id.
     *
     * @param id
     * @return item.
     */
    @GetMapping("/items/{id}")
    public Item findById(@PathVariable Integer id){

        LOGGER.debug("find item by id({})", id);

        return itemService.findItemById(id).orElseThrow(() -> new ItemNotFoundException(id));
    }

    /**
     * Add new item to date base.
     *
     * @param item
     * @return creates item Id.
     */
   @PostMapping(path = "/items", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> add(@RequestBody Item item) {
        LOGGER.debug("createItem({})", item);
        Integer id = itemService.createItem(item);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    /**
     * Update item.
     * @param item
     * @return number of updated records in database.
     */
    @PutMapping(value = "/items", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updateItem(@RequestBody Item item) {

        LOGGER.debug("updateItem({})", item);
        int result = itemService.updateItem(item);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * Delete item from database.
     *
     * @param id
     * @return number of deleted records in database.
     */
    @DeleteMapping(value = "/items/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deleteItem(@PathVariable Integer id) {

        int result = itemService.deleteItem(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }

}
