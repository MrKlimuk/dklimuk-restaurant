package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.OrderDao;
import com.epam.brest.courses.model.Order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;

    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public List<Order> findAllOrders() {
        return orderDao.findAllOrders();
    }

    @Override
    public Optional<Order> findOrderById(Integer orderId) {
        return orderDao.findOrderById(orderId);
    }

    @Override
    public Integer createOrder(Order order) {
        return orderDao.createOrder(order);
    }

    @Override
    public int update(Order order) {
        return orderDao.update(order);
    }

    @Override
    public int delete(Integer orderId) {
        return orderDao.delete(orderId);
    }
}
