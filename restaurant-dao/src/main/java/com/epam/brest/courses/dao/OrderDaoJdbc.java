package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Optional;

public class OrderDaoJdbc implements OrderDao {

    @Value("${ordertable.select}")
    private String findAllOrdersSql;



    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public OrderDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Order> findAllOrders() {
        List<Order> orders = namedParameterJdbcTemplate
                .query(findAllOrdersSql, BeanPropertyRowMapper.newInstance(Order.class));
        return orders;
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
