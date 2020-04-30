package com.epam.brest.courses.web_app;


import com.epam.brest.courses.model.Item;
import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.model.Position;
import com.epam.brest.courses.service.ItemService;
import com.epam.brest.courses.service.OrderService;
import com.epam.brest.courses.service.PositionService;
import com.epam.brest.courses.web_app.validators.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Order controller.
 */
@Controller
public class OrderController {

    /**
     * Object for communication with the order service level
     */
    private final OrderService orderService;

    /**
     * Object for communication with the item service level
     */
    private final ItemService itemService;

    /**
     * Object for communication with the position service level
     */
    private final PositionService positionService;

    /**
     * Object for communication with the validator
     */
    @Autowired
    OrderValidator orderValidator;

    /**
     * Constructor accepts service layer object.
     * @param orderService
     * @param itemService
     * @param positionService
     */
    public OrderController(OrderService orderService, ItemService itemService, PositionService positionService) {
        this.orderService = orderService;
        this.itemService = itemService;
        this.positionService = positionService;
    }

    /**
     * Goto order page
     *
     * @param model
     * @return order.html
     */
    @GetMapping(value = "/orders")
    public String goToOrdersPage(Model model){
        List<Order> orders = orderService.findAllOrders();
        model.addAttribute("orders", orders);
        return "orders";
    }

    /**
     * Search order by date
     *
     * @param startDateString
     * @param endDateString
     * @param model
     * @return order list with sorted orders.
     */
    @PostMapping(value = "/searchByDate")
    public String searchOrderByDate(@ModelAttribute("startDateString") String startDateString,
                                    @ModelAttribute("endDateString") String endDateString,
                                    Model model){
        LocalDate startDate, endDate;

        try {
            startDate = LocalDate.parse(startDateString);
        } catch (Exception ex){
            startDate = LocalDate.of(2000, 1 , 1);
        }

        try{
            endDate = LocalDate.parse(endDateString);
        } catch (Exception ex){
            endDate = LocalDate.now();
        }

        List<Order> orders = orderService.findOrdersByDate(startDate, endDate);
        model.addAttribute("orders", orders);
        return "orders";

    }

    /**
     * Goto add order page.
     *
     * @return orderAdd.html
     */
    @GetMapping(value = "/order/add")
    public String goToAddOrder(){
    return "orderAdd";
    }

    /**
     * Create order in database.
     *
     * @param dateString
     * @param order
     * @param result
     * @return orderEdit.html.
     */
    @PostMapping(value = "/orderAdd")
    public String addOrder(@ModelAttribute("orderDateString") String dateString,
                          @Valid Order order, BindingResult result){

        LocalDate date;
        try {
            date = LocalDate.parse(dateString);
        } catch (Exception ex) {
            date = LocalDate.now();
        }

        order.setOrderDate(date);

        orderValidator.validate(order, result);

        if (result.hasErrors()) {
            return "orderAdd";
        } else {
            try{
                Integer id = orderService.createOrder(order);
                return "redirect:/orders/edit/" + id;
            } catch (Exception e){
                return "redirect:order/add";
            }

        }
    }

    /**
     * Goto edit page with all list positions in order.
     *
     * @param id
     * @param model
     * @return orderEdit.html.
     */
    @GetMapping(value = "orders/edit/{id}")
    public String goToEditOrderPage(@PathVariable("id") Integer id,
                                    Model model ){
        Optional<Order> orderOptional =orderService.findOrderById(id);
        model.addAttribute(orderOptional.get());
        List<Item> items = itemService.findAllItem();
        model.addAttribute("items", items);
        List<Position> positions = positionService.findPositionByOrderId(id);
        model.addAttribute("positions", positions);
        return "orderEdit";
    }

    /**
     * Save modified order.
     *
     * @param order
     * @param result
     * @return orders.html with order list.
     */
    @PostMapping(value = "/orderEdit")
    public String updateOrder(@Valid Order order,
                              BindingResult result){

        orderValidator.validate(order, result);
        if(result.hasErrors()){
            return "redirect:orders/edit/" + String.valueOf(order.getOrderId());
        } else {
            try {
                orderService.update(order);
            } catch (Exception e) {
                return "redirect:orders/edit/" + String.valueOf(order.getOrderId());
            }
            return "redirect:/orders";
        }
    }

    /**
     * Add item to order.
     *
     * @param orderId
     * @param id
     * @return edit order page.
     */
    @GetMapping(value = "/orders/details/{orderId}/add/{id}")
    public String addItemToOrder(
            @PathVariable("orderId") Integer orderId,
            @PathVariable("id") Integer id){

        Optional<Item> optionalItem = itemService.findItemById(id);

        Position position = new Position();
        position.setPositionOrderId(orderId);
        position.setPositionName(optionalItem.get().getItemName());
        position.setPositionPrice( optionalItem.get().getItemPrice());
        position.setPositionCount(1);

        positionService.create(position);
        Optional<Order> optionalOrder = orderService.findOrderById(orderId);
        orderService.update(optionalOrder.get());
        String url = "/orders/edit/" + orderId;


        return "redirect:" + url;
    }

    /**
     * Delete item from order.
     *
     * @param id
     * @param orderId
     * @return edit order page.
     */
    @GetMapping(value = "/orders/details/{orderId}/delete/{id}")
    public String deleteItemFromOrder(@PathVariable("id") Integer id,
                                      @PathVariable("orderId") Integer orderId){
        positionService.delete(id);
        Optional<Order> optionalOrder = orderService.findOrderById(orderId);
        orderService.update(optionalOrder.get());
        String url = "/orders/edit/" + orderId;
        return "redirect:"+url;
    }

    /**
     * Delete order from data base.
     *
     * @param orderId
     * @return page with order list.
     */
    @GetMapping(value = "/orders/delete/{id}")
    public String deleteOrder(@PathVariable("id") Integer orderId){
        orderService.delete(orderId);
        return "redirect:/orders";
    }
}
