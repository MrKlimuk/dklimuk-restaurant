package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Item;
import com.epam.brest.courses.model.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.brest.courses.constants.ItemConstants.ITEM_NAME;
import static com.epam.brest.courses.constants.OrderConstants.*;

@Repository
public class OrderDaoJdbc implements OrderDao {

//    @Value("${ordertable.select}")
    private String findAllOrdersSql = "SELECT d.order_id, d.order_name, d.order_price, d.order_date FROM ordertable  AS d ORDER BY d.order_id";

//    @Value("${ordertable.findById}")
    private String findByIdSql = "SELECT order_id, order_name, order_price, order_date FROM ordertable WHERE order_id = :orderId";

//    @Value("${ordertable.create}")
    private String createOrderSql = "INSERT INTO ordertable (order_name, order_price, order_date) VALUES (:orderName, 0, :orderDate)";

//    @Value("${ordertable.update}")
    private String updateOrderSql = "UPDATE ordertable set order_name = :orderName, order_price = ( select sum(position_price)" +
        " from position where position_order_id = :orderId) where order_id =:orderId";

//    @Value("${ordertable.delete}")
    private String deleteOrderSql = "DELETE FROM ordertable WHERE order_id = :orderId";

//    @Value("${ordertable.findOrdersByDate}")
    private String findOrderByDateSql = "SELECT order_id, order_name, order_price, order_date FROM ordertable WHERE order_date BETWEEN :orderDateStart AND :orderDateEnd";

    private static final String CHECK_COUNT_NAME = "select count(order_id) from ordertable where lower(order_name) = lower(:orderName)";


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
    public List<Order> findOrdersByDate(LocalDate startDate, LocalDate endDate) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(ORDER_DATE_START, startDate);
        mapSqlParameterSource.addValue(ORDER_DATE_END, endDate);

        List<Order> orders = namedParameterJdbcTemplate.query(findOrderByDateSql, mapSqlParameterSource,
                BeanPropertyRowMapper.newInstance(Order.class));

        return orders;
    }

    @Override
    public Integer createOrder(Order order) {
        if (!isNameUnique(order)) {
            throw new IllegalArgumentException("Order with the same name already exists in DB.");
        }
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(ORDER_NAME, order.getOrderName());
        params.addValue(ORDER_DATE, order.getOrderDate());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(createOrderSql, params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    private boolean isNameUnique(Order order) {

        return namedParameterJdbcTemplate.queryForObject(CHECK_COUNT_NAME,
                new MapSqlParameterSource(ORDER_NAME, order.getOrderName()),
                Integer.class) == 0;
    }

    @Override
    public int update(Order order) {
        Map<String, Object> params = new HashMap<>();
        params.put(ORDER_ID, order.getOrderId());
        params.put(ORDER_NAME, order.getOrderName());
        return namedParameterJdbcTemplate.update(updateOrderSql, params);
    }

    @Override
    public int delete(Integer orderId) {
        MapSqlParameterSource mapSqlParameterSource =
                new MapSqlParameterSource();
        mapSqlParameterSource.addValue(ORDER_ID, orderId);
        return namedParameterJdbcTemplate.update(deleteOrderSql, mapSqlParameterSource);
    }

}
