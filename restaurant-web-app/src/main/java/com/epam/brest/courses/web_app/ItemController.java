package com.epam.brest.courses.web_app;

import com.epam.brest.courses.model.Item;
import com.epam.brest.courses.service.ItemService;
import com.epam.brest.courses.web_app.validators.ItemValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Item controller.
 */
@Controller
public class ItemController {

    /**
     * Object for communication with the item service level
     */
    private final ItemService itemService;

    /**
     * Object for communication with the validator
     */
    @Autowired
    ItemValidator itemValidator;

    /**
     * Constructor accepts service layer object.
     * @param itemService
     */
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Go to hello page
     *
     * @return hello.html
     */
    @GetMapping(value = "/")
    public String goToHelloPage(){
        return "hello";
    }

    /**
     * Goto page with all items.
     *
     * @param model
     * @return page with menu.
     */
    @GetMapping(value = "/items")
    public String goToItemsPage(Model model){
        List<Item> items = itemService.findAllItem();
        model.addAttribute("items", items);
        return "items";
    }

    /**
     * Goto add item page.
     *
     * @return itemAdd.html.
     */
    @GetMapping(value = "/item/add")
    public String goToAddItemPage(){
        return "itemAdd";
    }

    /**
     * Created item in database.
     *
     * @param item
     * @param result
     * @return menu page.
     */
    @PostMapping(value = "/item/add")
    public String  addItem(@Valid Item item,
                           BindingResult result){

        itemValidator.validate(item, result);

        if( result.hasErrors()){
            return "itemAdd";
        } else {
            try{
                itemService.createItem(item);
            } catch (Exception ex){
                return "redirect:/item/add";
            }
            return "redirect:/items";
        }
    }


    /**
     * Goto item edit page.
     * @param id
     * @param model
     * @return itemEdit.html.
     */
    @GetMapping(value = "/items/edit/{id}")
    public String goToEditItemPage(@PathVariable("id") Integer id,
                                   Model model){
        Optional<Item> itemOptional = itemService.findItemById(id);
        model.addAttribute(itemOptional.get());
        return "itemEdit";
    }

    /**
     * Update item in database.
     *
     * @param item
     * @param result
     * @return menu page.
     */
    @PostMapping(value = "/items/edit/{id}")
    public String updateItem(@Valid Item item,
                             BindingResult result){

        itemValidator.validate(item, result);
        if(result.hasErrors()){
            return "itemEdit";
        } else {
            try{
                this.itemService.updateItem(item);
            } catch (Exception ex){
                return "redirect:items/edit/" + String.valueOf(item.getItemId());
            }

            return "redirect:/items";
        }
    }

    /**
     * Delete item from database.
     *
     * @param itemId
     * @return menu page.
     */
    @GetMapping(value = "/items/delete/{id}")
    public String deleteItemById(@PathVariable("id") Integer itemId){
        itemService.deleteItem(itemId);
        return "redirect:/items";
    }
}

