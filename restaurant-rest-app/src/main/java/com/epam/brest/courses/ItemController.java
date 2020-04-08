package com.epam.brest.courses;


import com.epam.brest.courses.model.Item;
import com.epam.brest.courses.service.ItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController(value = "/api")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;

    }

    @GetMapping(value = "/items")
    public final List<Item> items(){
        return itemService.findAllItem();
    }
}
