package com.epam.brest.courses.web_app;


import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service.OrderService;
import jdk.vm.ci.meta.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

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
}
