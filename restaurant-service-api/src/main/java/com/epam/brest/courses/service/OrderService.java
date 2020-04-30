package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * The interface provides access to service methods.
 */
public interface OrderService {

    /**
     * Find all orders.
     *
     * @return order list.
     */
    List<Order> findAllOrders();

    /**
     * Find order by id.
     *
     * @param orderId order Id.
     * @return order.
     */
    Optional<Order> findOrderById(Integer orderId);

    /**
     * Find order by date.
     *
     * @param startDate start date.
     * @param endDate end date.
     * @return orders are placed between two dates.
     */
    List<Order> findOrdersByDate(LocalDate startDate, LocalDate endDate);

    /**
     * Create order.
     *
     * @param order order.
     * @return created order id.
     */
    Integer createOrder(Order order);

    /**
     * Update order.
     *
     * @param order order.
     * @return number of updated records in the database.
     */
    int update(Order order);

    /**
     * Delete order.
     *
     * @param orderId order id.
     * @return the number of rows affected.
     */
    int delete(Integer orderId);
}
