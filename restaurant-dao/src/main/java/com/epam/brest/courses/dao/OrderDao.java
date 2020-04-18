package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderDao {
    List<Order> findAllOrders();
    Optional<Order> findOrderById(Integer orderId);
    List<Order> findOrdersByDate(LocalDate startDate, LocalDate endDate);
    Integer createOrder(Order order);
    int update(Order order);
    int delete(Integer orderId);
}
