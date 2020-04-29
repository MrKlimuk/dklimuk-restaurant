package com.epam.brest.courses.rest;

import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.rest.exception.OrderNotFoundException;
import com.epam.brest.courses.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Rest controller for order.
 */
@RestController
public class OrderController {

    /**
     * Default logger for order class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    /**
     * Object for communication with the order level
     */
    private final OrderService orderService;

    /**
     * Constructor accepts service layer object.
     *
     * @param orderService
     */
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Find all orders.
     *
     * @returnl ist with all found orders.
     */
    @GetMapping(value = "/orders")
    public List<Order> findAllOrders(){

        LOGGER.debug("orders()");

        return orderService.findAllOrders();
    }

    /**
     * Find order by id.
     *
     * @param id
     * @return order.
     */
    @GetMapping("/orders/{id}")
    public Order findById(@PathVariable Integer id){

        LOGGER.debug("find order by id({})", id);

        return orderService.findOrderById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    /**
     * Find order by date.
     *
     * @param startDateStr start date.
     * @param endDateStr end date.
     * @return orders are placed between two dates.
     */
    @GetMapping("orders/search/{startDate}/{endDate}")
    public List<Order> findOrderByDate(@PathVariable("startDate") String startDateStr,
                                       @PathVariable("endDate") String endDateStr){
        LOGGER.debug("findOrderByDate({},{})", startDateStr, endDateStr);

        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        return orderService.findOrdersByDate(startDate, endDate);

    }


    /**
     * Add new order to database.
     *
     * @param order order.
     * @return created order id.
     */
    @PostMapping(path = "/orders", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> addOrder(@RequestBody Order order) {
        LOGGER.debug("createOrder({})", order);
        Integer id = orderService.createOrder(order);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    /**
     * Update order.
     *
     * @param order
     * @retur number of updated records in the database.
     */
    @PutMapping(value = "/orders", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updateOrder(@RequestBody Order order) {

        LOGGER.debug("updateOrder({})", order);
        int result = orderService.update(order);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * Delete order.
     *
     * @param id order id.
     * @return the number of rows affected.
     */
    @DeleteMapping(value = "/orders/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deleteOrder(@PathVariable Integer id) {

        int result = orderService.delete(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
