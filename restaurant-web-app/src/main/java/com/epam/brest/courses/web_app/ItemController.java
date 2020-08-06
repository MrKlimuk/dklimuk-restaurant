package com.epam.brest.courses.web_app;

import com.epam.brest.courses.model.Item;
import com.epam.brest.courses.service.ItemService;
import com.epam.brest.courses.web_app.validators.ItemValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Item controller.
 */
@Controller
public class ItemController {



    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

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
//     */
//    @GetMapping(value = "/items")
//    public String goToItems(Model model){
//        List<Item> items = itemService.findAllItem();
//        model.addAttribute("items", items);
//        return "items";
//    }

    @GetMapping(value = "/items")
    public String goToItemsPage(Model model,
                                @RequestParam(defaultValue = "0", name = "page") String pageString
                               ){
        Integer page = Integer.valueOf(pageString);
        LOGGER.info("Item controller page = ({})", pageString);

        List<Item> items = itemService.findAllItemPage(Integer.valueOf(page), 5);
        model.addAttribute("items", items);
        LOGGER.info("Item controller items = ({})", items);


        Integer totalPages = itemService.getItemTotalPages(Integer.valueOf(page), 5) - 1;
        LOGGER.info("total page({})", totalPages);
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages)
                    .boxed()
                    .collect(Collectors.toList());

            LOGGER.info("pageNumbers({})", pageNumbers);

            model.addAttribute("totalPages", totalPages);
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "items";
    }

    @GetMapping(value = "items/download/items")
    public ResponseEntity<InputStreamResource> excelCustomersReport() throws IOException {
        List<Item> items= itemService.findAllItem();

        ByteArrayInputStream in = ItemExelGenerator.itemsToExcel(items);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=items.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
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

        LOGGER.info("Item name: ({})", item.getItemName());
        LOGGER.info("Item price: ({})", item.getItemPrice());

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
     * Open generate items page
     *
     * @return itemGenerate.html
     */
    @GetMapping(value = "/items/generate")
    public String gotoGenerateItemsPage(){


        return "itemGenerate";
    }

    /**
     * send request to generate items
     *
     * @param number
     * @param language
     * @return list items
     */
    @PostMapping(value = "/items/generate")
    public String  generateItems(@RequestParam(defaultValue = "1", name = "number") Integer number,
                                 @RequestParam(defaultValue = "EN", name = "language") String language){

        LOGGER.info("number: ({})", number);
        LOGGER.info("language: ({})", language);

        itemService.generateItem(number, language);

        return "redirect:/items";
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

