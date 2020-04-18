package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.constants.OrderConstants.ORDER_NAME_SIZE;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
public class OrderServiceRestTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceRestTest.class);


    public static final String URL = "http://localhost:8088/orders";

    private final BigDecimal ORDER_PRICE = new BigDecimal(100);
    private final LocalDate DATE = LocalDate.of(2020, 4, 18);

    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockRestServiceServer;

    private ObjectMapper mapper = new ObjectMapper();

    OrderServiceRest orderServiceRest;

    @BeforeEach
    public void before(){
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        orderServiceRest = new OrderServiceRest(URL, restTemplate);
    }

    @Test
    public void shouldFindAllOrder() throws Exception {

        LOGGER.debug("shouldFindAllOrders");
        // given
        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(new URI(URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                );

        // when
        List<Order> orders = orderServiceRest.findAllOrders();

        // then
        mockRestServiceServer.verify();
        assertNotNull(orders);
        assertTrue(orders.size() > 0);
    }

    @Test
    public void shouldFindOrderById() throws Exception {

        // given
        Integer id = 1;
        Order order = new Order()
                .setOrderId(id)
                .setOrderName(RandomStringUtils.randomAlphabetic(ORDER_NAME_SIZE))
                .setOrderPrice(ORDER_PRICE)
                .setOrderDate(DATE);

        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(new URI(URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(order))
                );

        // when
        Optional<Order> orderOptional = orderServiceRest.findOrderById(id);

        // then
        mockRestServiceServer.verify();
        assertTrue(orderOptional.isPresent());
        assertEquals(orderOptional.get().getOrderId(), id);
        assertEquals(orderOptional.get().getOrderName(), order.getOrderName());
        assertEquals(orderOptional.get().getOrderPrice(), order.getOrderPrice());
    }

    @Test
    public void shouldCreateOrder() throws Exception {

        LOGGER.debug("shouldCreateOrder()");
        // given
        Order order = new Order()
                .setOrderName(RandomStringUtils.randomAlphabetic(ORDER_NAME_SIZE))
                .setOrderPrice(ORDER_PRICE);

        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(new URI(URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );
        // when
        Integer id = orderServiceRest.createOrder(order);

        // then
        mockRestServiceServer.verify();
        assertNotNull(id);
    }


    @Test
    public void shouldUpdateOrder() throws Exception {

        // given
        Integer id = 1;
        Order order = new Order()
                .setOrderId(id)
                .setOrderName(RandomStringUtils.randomAlphabetic(ORDER_NAME_SIZE))
                .setOrderPrice(ORDER_PRICE);

        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(new URI(URL)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );

        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(new URI(URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(order))
                );

        // when
        int result = orderServiceRest.update(order);
        Optional<Order> updateOrderOptional = orderServiceRest.findOrderById(id);

        // then
        mockRestServiceServer.verify();
        assertTrue(1 == result);

        assertTrue(updateOrderOptional.isPresent());
        assertEquals(updateOrderOptional.get().getOrderId(), id);
        assertEquals(updateOrderOptional.get().getOrderName(), order.getOrderName());
        assertEquals(updateOrderOptional.get().getOrderPrice(), order.getOrderPrice());
    }

    @Test
    public void shouldDeleteOrder() throws Exception {

        // given
        Integer id = 1;
        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(new URI(URL + "/" + id)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );
        // when
        int result = orderServiceRest.delete(id);

        // then
        mockRestServiceServer.verify();
        assertTrue(1 == result);
    }


    private Order create(int index) {
        Order order = new Order();
        order.setOrderId(index);
        order.setOrderName("nameOrder" + index);
        order.setOrderPrice(new BigDecimal(100));
        return order;
    }


}
