package com.epam.brest.courses.web_app;


import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service.OrderService;
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
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/orders")
    public String goToOrdersPage(Model model){
        List<Order> orders = orderService.findAllOrders();
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping(value = "/orders/delete/{id}")
    public String deleteOrder(@PathVariable("id") Integer orderId){
        orderService.delete(orderId);
        return "redirect:/orders";
    }

    @GetMapping(value = "/order/add")
    public String goToAddOrder(){

    return "orderAdd";
    }

    @PostMapping(value = "/orderAdd")
    public String addOrder(@Valid Order order){
        orderService.createOrder(order);
        return "redirect:/orders";
    }

    @GetMapping(value = "orders/edit/{id}")
    public String goToEditOrderPage(@PathVariable("id") Integer id,
                                    Model model ){
        Optional<Order> orderOptional =orderService.findOrderById(id);
        model.addAttribute(orderOptional.get());
        return "orderEdit";
    }

    @PostMapping(value = "/orderEdit")
    public String updateOrder(@Valid Order order,
                              BindingResult result){
        orderService.update(order);
        return "redirect:/orders";
    }



}
