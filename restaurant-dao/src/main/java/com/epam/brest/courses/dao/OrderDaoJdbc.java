package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Order;

import java.util.List;
import java.util.Optional;

public class OrderDaoJdbc implements OrderDao {
    @Override
    public List<Order> findAllOrders() {
        return null;
    }

    @Override
    public Optional<Order> findOrderById() {
        return Optional.empty();
    }

    @Override
    public Integer createOrder(Order order) {
        return null;
    }

    @Override
    public int update(Order order) {
        return 0;
    }

    @Override
    public int delete(Integer orderId) {
        return 0;
    }
}
