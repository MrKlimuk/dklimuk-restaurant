package com.epam.brest.courses.web_app;

import com.epam.brest.courses.model.Item;
import com.epam.brest.courses.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(value = "/")
    public String goToHelloPage(){
        return "hello";
    }

    @GetMapping(value = "/items")
    public String goToItemsPage(Model model){
        List<Item> items = itemService.findAllItem();
        model.addAttribute("items", items);
        return "items";
    }

    @GetMapping(value = "/goToAddItemPage")
    public String goToAddItemPage(){
        return "item";
    }

    @PostMapping(value = "/itemAdd")
    public String  addItem(@Valid Item item){
        itemService.createItem(item);
        return "redirect:/items";
    }






}

