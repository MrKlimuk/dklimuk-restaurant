package com.epam.brest.courses.rest;

import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.rest.exception.OrderNotFoundException;
import com.epam.brest.courses.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/orders")
    public List<Order> findAllOrders(){

        LOGGER.debug("orders()");

        return orderService.findAllOrders();
    }

    @GetMapping("/orders/{id}")
    public Order findById(@PathVariable Integer id){

        LOGGER.debug("find order by id({})", id);

        return orderService.findOrderById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }


    @PostMapping(path = "/orders", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> addOrder(@RequestBody Order order) {
        LOGGER.debug("createOrder({})", order);
        Integer id = orderService.createOrder(order);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping(value = "/orders", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updateOrder(@RequestBody Order order) {

        LOGGER.debug("updateOrder({})", order);
        int result = orderService.update(order);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/orders/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deleteOrder(@PathVariable Integer id) {

        int result = orderService.delete(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
