package com.epam.brest.courses.web_app;


import com.epam.brest.courses.model.Item;
import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.model.Position;
import com.epam.brest.courses.service.ItemService;
import com.epam.brest.courses.service.OrderService;
import com.epam.brest.courses.service.PositionService;
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

@Controller
public class OrderController {

    private final OrderService orderService;
    private final ItemService itemService;
    private final PositionService positionService;

    public OrderController(OrderService orderService, ItemService itemService, PositionService positionService) {
        this.orderService = orderService;
        this.itemService = itemService;
        this.positionService = positionService;
    }

    @GetMapping(value = "/orders")
    public String goToOrdersPage(Model model){
        List<Order> orders = orderService.findAllOrders();
        model.addAttribute("orders", orders);
        return "orders";
    }

    @PostMapping(value = "/searchByDate")
    public String searchOrderByDate(@ModelAttribute("startDateString") String startDateString,
                                    @ModelAttribute("endDateString") String endDateString,
                                    Model model){
        LocalDate startDate, endDate;

        try {
            startDate = LocalDate.parse(startDateString);
            endDate = LocalDate.parse(endDateString);
        } catch (Exception ex){
            startDate = LocalDate.of(2000, 1 , 1);
//            endDate = LocalDate.now();
//            endDate = LocalDate.of(2030, 1, 1);
            endDate = LocalDate.now();
        }
        List<Order> orders = orderService.findOrdersByDate(startDate, endDate);
        model.addAttribute("orders", orders);
        return "orders";

    }


    @GetMapping(value = "/order/add")
    public String goToAddOrder(){
    return "orderAdd";
    }

    @PostMapping(value = "/orderAdd")
    public String addOrder(@ModelAttribute("orderDateString") String dateString,
                          @Valid Order order, BindingResult result){

        LocalDate date;
        date = LocalDate.parse(dateString);
        order.setOrderDate(date);
        if (result.hasErrors()) {
            return "orderAdd";
        } else {
            Integer id = orderService.createOrder(order);

            return "redirect:/orders/edit/" + id;
        }
    }




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

    @PostMapping(value = "/orderEdit")
    public String updateOrder(@Valid Order order,
                              BindingResult result){
        orderService.update(order);
        return "redirect:/orders";
    }

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

    @GetMapping(value = "/orders/details/{orderId}/delete/{id}")
    public String deleteItemFromOrder(@PathVariable("id") Integer id,
                                      @PathVariable("orderId") Integer orderId){
        positionService.delete(id);
        Optional<Order> optionalOrder = orderService.findOrderById(orderId);
        orderService.update(optionalOrder.get());
        String url = "/orders/edit/" + orderId;
        return "redirect:"+url;
    }

    @GetMapping(value = "/orders/delete/{id}")
    public String deleteOrder(@PathVariable("id") Integer orderId){
        orderService.delete(orderId);
        return "redirect:/orders";
    }
}
