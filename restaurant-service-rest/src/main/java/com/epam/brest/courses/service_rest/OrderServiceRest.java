package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

public class OrderServiceRest implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public OrderServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Order> findAllOrders() {

        LOGGER.debug("findAllOrder()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<Order>) responseEntity.getBody();
    }

    @Override
    public Optional<Order> findOrderById(Integer orderId) {

        LOGGER.debug("findOrderById({})", orderId);
        ResponseEntity<Order> responseEntity =
                restTemplate.getForEntity(url + "/" + orderId, Order.class);
        return Optional.ofNullable(responseEntity.getBody());
    }

    @Override
    public Integer createOrder(Order order) {

        LOGGER.debug("createOrder({})", order);
        ResponseEntity responseEntity = restTemplate.postForEntity(url, order, Integer.class);
        Object result = responseEntity.getBody();
        return (Integer) result;
    }

    @Override
    public int update(Order order) {

        LOGGER.debug("update({})", order);
        restTemplate.put(url, order);
        return 1;
    }

    @Override
    public int delete(Integer orderId) {

        LOGGER.debug("deleteOrder({})", orderId);
        restTemplate.delete(url + "/" + orderId);
        return 1;
    }
}
