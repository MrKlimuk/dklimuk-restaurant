package com.epam.brest.courses;


import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service.OrderService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.constants.OrderConstants.ORDER_NAME_SIZE;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({SpringExtension.class})
@ContextConfiguration({"classpath*:test-db.xml", "classpath*:test-service.xml", "classpath:dao.xml"})
public class OrderServiceImplIT {

    private final OrderService orderService;
    private static BigDecimal PRICE = new BigDecimal(0);
    private static BigDecimal PRICE_FOR_UPDATE = new BigDecimal(50);


    @Autowired
    public OrderServiceImplIT(OrderService orderService) {
        this.orderService = orderService;
    }

    @Test
    public void shouldFindAllOrders(){
        List<Order> orders = orderService.findAllOrders();
        assertNotNull(orders);
        assertTrue(orders.size() > 0);
    }

    @Test
    public void shouldFindOrderById(){
        Order order = new Order()
                .setOrderName(RandomStringUtils.randomAlphabetic(ORDER_NAME_SIZE))
                .setOrderPrice(PRICE);

        Integer id = orderService.createOrder(order);
        Optional<Order> orderOptional = orderService.findOrderById(id);

        Assertions.assertTrue(orderOptional.isPresent());
        assertEquals(orderOptional.get().getOrderId(), id);
        assertEquals(orderOptional.get().getOrderName(), order.getOrderName());
        assertEquals(orderOptional.get().getOrderPrice(), order.getOrderPrice());
    }

    @Test
    public void shouldCreateOrder(){
        Order order = new Order()
                .setOrderName(RandomStringUtils.randomAlphabetic(ORDER_NAME_SIZE));
        Integer id = orderService.createOrder(order);
        assertNotNull(id);
    }

    @Test
    public void shouldUpdateOrder(){
        Order order = new Order()
                .setOrderName(RandomStringUtils.randomAlphabetic(ORDER_NAME_SIZE));
        Integer id = orderService.createOrder(order);
        assertNotNull(id);

        Optional<Order> orderOptional = orderService.findOrderById(id);
        Assertions.assertTrue(orderOptional.isPresent());

        orderOptional.get().setOrderName(RandomStringUtils.randomAlphabetic(ORDER_NAME_SIZE));

        int result = orderService.update(orderOptional.get());

        assertTrue(1 == result);

        Optional<Order> updateOrderOptional = orderService.findOrderById(id);
        Assertions.assertTrue(updateOrderOptional.isPresent());
        assertEquals(updateOrderOptional.get().getOrderId(), orderOptional.get().getOrderId());
        assertEquals(updateOrderOptional.get().getOrderName(), orderOptional.get().getOrderName());
//        assertEquals(updateOrderOptional.get().getOrderPrice(), orderOptional.get().getOrderPrice());

    }

    @Test
    public void shouldDeleteOrder(){
        Order order = new Order().
                setOrderName(RandomStringUtils.randomAlphabetic(ORDER_NAME_SIZE));
        Integer id = orderService.createOrder(order);

        List<Order> orders = orderService.findAllOrders();
        assertNotNull(orders);

        int result = orderService.delete(id);
        assertTrue(1 == result);

        List<Order> currentOrder = orderService.findAllOrders();
        assertNotNull(currentOrder);

        assertTrue(orders.size() - 1 == currentOrder.size());
    }
}
