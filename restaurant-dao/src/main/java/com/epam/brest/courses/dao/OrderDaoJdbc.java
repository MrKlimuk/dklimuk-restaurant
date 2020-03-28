package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.constants.OrderConstants.ORDER_ID;
import static com.epam.brest.courses.constants.OrderConstants.ORDER_NAME;

public class OrderDaoJdbc implements OrderDao {

    @Value("${ordertable.select}")
    private String findAllOrdersSql;

    @Value("${ordertable.findById}")
    private String findByIdSql;

    @Value("${ordertable.create}")
    private String createOrderSql;

    @Value("${ordertable.delete}")
    private String deleteOrderSql;



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
    public Optional<Order> findOrderById(Integer orderId) {
        SqlParameterSource namedParameter = new MapSqlParameterSource(ORDER_ID, orderId);
        List<Order> results = namedParameterJdbcTemplate.query(findByIdSql, namedParameter,
                BeanPropertyRowMapper.newInstance(Order.class));
        return Optional.ofNullable(DataAccessUtils.uniqueResult(results));
    }

    @Override
    public Integer createOrder(Order order) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(ORDER_NAME, order.getOrderName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(createOrderSql, params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int update(Order order) {
        return 0;
    }

    @Override
    public int delete(Integer orderId) {
        MapSqlParameterSource mapSqlParameterSource =
                new MapSqlParameterSource();
        mapSqlParameterSource.addValue(ORDER_ID, orderId);
        return namedParameterJdbcTemplate.update(deleteOrderSql, mapSqlParameterSource);
    }

}
