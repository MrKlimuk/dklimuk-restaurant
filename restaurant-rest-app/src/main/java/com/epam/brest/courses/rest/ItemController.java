package com.epam.brest.courses.rest;


import com.epam.brest.courses.model.Item;
import com.epam.brest.courses.rest.exception.ItemNotFoundException;
import com.epam.brest.courses.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
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

    @GetMapping(value = "/itemsPage")
    public final List<Item> itemsPage(
            @RequestParam(defaultValue = "0", name = "pageNumber") Integer pageNumber,
            @RequestParam(defaultValue = "5", name = "pageSize") Integer pageSize
    ){

        LOGGER.debug("itemsPage()");
        return itemService.findAllItemPage(pageNumber, pageSize);
    }

    @GetMapping(value = "/itemsTotalPages")
    public final Integer getItemsTotalPages(
            @RequestParam(defaultValue = "0", name = "pageNumber") Integer pageNumber,
            @RequestParam(defaultValue = "5", name = "pageSize") Integer pageSize
    ){

        LOGGER.debug("itemsPage()");
        return itemService.getItemTotalPages(pageNumber, pageSize);
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
     * Generate items
     * @param number
     * @param language
     * @return
     */
    @GetMapping("items/generate")
    public List<Item> generateItems(@RequestParam(defaultValue = "1", name = "number") Integer number,
                                    @RequestParam(defaultValue = "EN", name = "language") String language){

        itemService.generateItem(number, language);
        return itemService.findAllItem();
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

    /**
     * Delete all items
     *
     * @return
     * @throws SQLException
     */
    @GetMapping("/items/delete")
    public ResponseEntity deleteAllItems() throws SQLException {
        LOGGER.info("delete all items controller");
        itemService.deleteAllItems();
        return new ResponseEntity(HttpStatus.OK);
    }

}
