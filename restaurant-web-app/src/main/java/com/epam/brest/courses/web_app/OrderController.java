package com.epam.brest.courses.web_app;


import com.epam.brest.courses.service.OrderService;
import jdk.vm.ci.meta.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/orders")
    public String goToOrdersPage(){
        return "orders";
    }
}
