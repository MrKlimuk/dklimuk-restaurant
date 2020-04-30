package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.OrderDao;
import com.epam.brest.courses.model.Order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * A class that defines a set of operations
 * with an order model.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    /**
     * A order data access object.
     */
    private final OrderDao orderDao;

    /**
     * Constructor accepts dao layer object.
     *
     * @param orderDao
     */
    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    /**
     * Find all orders.
     *
     * @return order list.
     */
    @Override
    public List<Order> findAllOrders() {
        return orderDao.findAllOrders();
    }

    /**
     * Find order by id.
     *
     * @param orderId order Id.
     * @return order.
     */
    @Override
    public Optional<Order> findOrderById(Integer orderId) {
        return orderDao.findOrderById(orderId);
    }

    /**
     * Find order by date.
     *
     * @param startDate start date.
     * @param endDate end date.
     * @return orders are placed between two dates.
     */
    @Override
    public List<Order> findOrdersByDate(LocalDate startDate, LocalDate endDate) {
        return orderDao.findOrdersByDate(startDate, endDate);
    }

    /**
     * Create order.
     *
     * @param order order.
     * @return created order id.
     */
    @Override
    public Integer createOrder(Order order) {
        return orderDao.createOrder(order);
    }

    /**
     * Update order.
     *
     * @param order order.
     * @return number of updated records in the database.
     */
    @Override
    public int update(Order order) {
        return orderDao.update(order);
    }

    /**
     * Delete order.
     *
     * @param orderId order id.
     * @return the number of rows affected.
     */
    @Override
    public int delete(Integer orderId) {
        return orderDao.delete(orderId);
    }
}
