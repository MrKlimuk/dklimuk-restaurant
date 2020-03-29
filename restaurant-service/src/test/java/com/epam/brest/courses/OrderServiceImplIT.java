package com.epam.brest.courses;


import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({SpringExtension.class})
@ContextConfiguration({"classpath*:test-db.xml", "classpath*:test-service.xml", "classpath:dao.xml"})
public class OrderServiceImplIT {

    private final OrderService orderService;

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
}
