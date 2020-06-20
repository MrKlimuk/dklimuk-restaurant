package com.epam.brest.courses.dao;


import com.epam.brest.courses.model.Order;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.constants.OrderConstants.ORDER_NAME_SIZE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("dev")
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath:dao.xml"})
public class OrderDaoJdbcIT {

    private final OrderDao orderDao;
    private static BigDecimal PRICE = new BigDecimal(0);
    private static BigDecimal PRICE_FOR_UPDATE = new BigDecimal(50);
    private final LocalDate DATE = LocalDate.of(2020, 4, 18);
    private final LocalDate START_DATE = LocalDate.of(2020, 4, 2);
    private final LocalDate END_DATE = LocalDate.of(2020, 4, 18);



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
    public void shouldFindOrderById(){
        Order order = new Order()
                .setOrderName(RandomStringUtils.randomAlphabetic(ORDER_NAME_SIZE))
                .setOrderPrice(PRICE)
                .setOrderDate(DATE);


        Integer id = orderDao.createOrder(order);
        Optional<Order> orderOptional = orderDao.findOrderById(id);

        Assertions.assertTrue(orderOptional.isPresent());
        assertEquals(orderOptional.get().getOrderId(), id);
        assertEquals(orderOptional.get().getOrderName(), order.getOrderName());
        assertEquals(orderOptional.get().getOrderPrice(), order.getOrderPrice());
        assertEquals(orderOptional.get().getOrderDate(), order.getOrderDate());
    }

    @Test
    public void shouldCreateOrder(){
        Order order = new Order()
                .setOrderName(RandomStringUtils.randomAlphabetic(ORDER_NAME_SIZE))
                .setOrderDate(DATE);

        Integer id = orderDao.createOrder(order);
        assertNotNull(id);
    }

    @Test
    public void shouldFindOrderByDate(){
        List<Order> orders = orderDao.findOrdersByDate(START_DATE, END_DATE);

        assertNotNull(orders);
        assertFalse(orders.isEmpty());
    }

    @Test
    public void shouldUpdateOrder(){
        Order order = new Order()
                .setOrderName(RandomStringUtils.randomAlphabetic(ORDER_NAME_SIZE))
                .setOrderPrice(PRICE)
                .setOrderDate(DATE);
        Integer id = orderDao.createOrder(order);
        assertNotNull(id);

        Optional<Order> orderOptional = orderDao.findOrderById(1);
        Assertions.assertTrue(orderOptional.isPresent());

        orderOptional.get().setOrderName(RandomStringUtils.randomAlphabetic(ORDER_NAME_SIZE));

        int result = orderDao.update(orderOptional.get());

        assertTrue(1 == result);

        Optional<Order> updateOrderOptional  =orderDao.findOrderById(1);
        Assertions.assertTrue(updateOrderOptional.isPresent());
        assertEquals(updateOrderOptional.get().getOrderId(), (Integer) 1);
        assertEquals(updateOrderOptional.get().getOrderName(), orderOptional.get().getOrderName());
        assertEquals(updateOrderOptional.get().getOrderPrice(), orderOptional.get().getOrderPrice());
        assertEquals(updateOrderOptional.get().getOrderDate(), orderOptional.get().getOrderDate());
        assertEquals(updateOrderOptional.get().getOrderDate(), orderOptional.get().getOrderDate());

    }

    @Test
    public void shouldDeleteOrder(){
        Order order = new Order().
                setOrderName(RandomStringUtils.randomAlphabetic(ORDER_NAME_SIZE))
                .setOrderDate(DATE);

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
