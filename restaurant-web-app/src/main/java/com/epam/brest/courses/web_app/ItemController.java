package com.epam.brest.courses.web_app;

import com.epam.brest.courses.model.Item;
import com.epam.brest.courses.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

    @GetMapping(value = "/item/add")
    public String goToAddItemPage(){
        return "itemAdd";
    }

    @PostMapping(value = "/itemAdd")
    public String  addItem(@Valid Item item){
        itemService.createItem(item);
        return "redirect:/items";
    }




    @GetMapping(value = "/items/edit/{id}")
    public String goToEditItemPage(@PathVariable("id") Integer id,
                                   Model model){
        Optional<Item> itemOptional = itemService.findItemById(id);
        model.addAttribute(itemOptional.get());
        return "itemEdit";
    }

    @PostMapping(value = "/itemEdit")
    public String updateItem(@Valid Item item,
                             BindingResult result){
        if(result.hasErrors()){
            return "itemEdit";
        } else {
            this.itemService.updateItem(item);
            return "redirect:/items";
        }
    }


    @GetMapping(value = "/items/delete/{id}")
    public String deleteItemById(@PathVariable("id") Integer itemId){
        itemService.deleteItem(itemId);
        return "redirect:/items";
    }






}

