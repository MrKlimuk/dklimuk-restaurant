package com.epam.brest.courses.dao;


import com.epam.brest.courses.model.Order;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static com.epam.brest.courses.constants.OrderConstants.ORDER_NAME_SIZE;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath:dao.xml"})
public class OrderDaoJdbcIT {

    private final OrderDao orderDao;
    private static BigDecimal PRICE = new BigDecimal(100);


    @Autowired
    public OrderDaoJdbcIT(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Test
    public void shouldFindAllOrders(){
        List<Order> orders = orderDao.findAllOrders();
        assertNotNull(orders);
        assertTrue(orders.size() > 0);
    }

    @Test
    public void shouldCreateOrder(){
        Order order = new Order()
                .setOrderName(RandomStringUtils.randomAlphabetic(ORDER_NAME_SIZE));
        Integer id = orderDao.createOrder(order);
        assertNotNull(id);
    }

    @Test
    public void shouldDeleteOrder(){
        Order order = new Order().
                setOrderName(RandomStringUtils.randomAlphabetic(ORDER_NAME_SIZE));
        Integer id = orderDao.createOrder(order);

        List<Order> orders = orderDao.findAllOrders();
        assertNotNull(orders);

        int result = orderDao.delete(id);
        assertTrue(1 == result);

        List<Order> currentOrder = orderDao.findAllOrders();
        assertNotNull(currentOrder);

        assertTrue(orders.size() - 1 == currentOrder.size());
    }



}
