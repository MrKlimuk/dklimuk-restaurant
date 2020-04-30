package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Gets data from rest in JSON format.
 */
public class OrderServiceRest implements OrderService {

    /**
     * Logger for OrderServiceRest.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceRest.class);

    /**
     * URL rest-app.
     */
    private String url;

    /**
     * Client to perform HTTP requests.
     */
    private RestTemplate restTemplate;

    /**
     * Constructor accepts URL and restTemplate.
     *
     * @param url url.
     * @param restTemplate rest template.
     */
    public OrderServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    /**
     * Find all orders.
     *
     * @return order list.
     */
    @Override
    public List<Order> findAllOrders() {

        LOGGER.debug("findAllOrder()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<Order>) responseEntity.getBody();
    }

    /**
     * Find order by id.
     *
     * @param orderId order Id.
     * @return order.
     */
    @Override
    public Optional<Order> findOrderById(Integer orderId) {

        LOGGER.debug("findOrderById({})", orderId);
        ResponseEntity<Order> responseEntity =
                restTemplate.getForEntity(url + "/" + orderId, Order.class);
        return Optional.ofNullable(responseEntity.getBody());
    }

    /**
     * Find order by date.
     *
     * @param startDate start date.
     * @param endDate end date.
     * @return orders are placed between two dates.
     */
    @Override
    public List<Order> findOrdersByDate(LocalDate startDate, LocalDate endDate) {

        LOGGER.debug("findOrderByDate({}, {})", startDate, endDate);
        ResponseEntity responseEntity = restTemplate.getForEntity(url + "/search/" + startDate + "/" + endDate, List.class);
        return (List<Order>) responseEntity.getBody();
    }

    /**
     * Create order.
     *
     * @param order order.
     * @return created order id.
     */
    @Override
    public Integer createOrder(Order order) {

        LOGGER.debug("createOrder({})", order);
        ResponseEntity responseEntity = restTemplate.postForEntity(url, order, Integer.class);
        Object result = responseEntity.getBody();
        return (Integer) result;
    }

    /**
     * Update order.
     *
     * @param order order.
     * @return number of updated records in the database.
     */
    @Override
    public int update(Order order) {

        LOGGER.debug("update({})", order);
        restTemplate.put(url, order);
        return 1;
    }

    /**
     * Delete order.
     * @param orderId order id.
     * @return the number of rows affected.
     */
    @Override
    public int delete(Integer orderId) {

        LOGGER.debug("deleteOrder({})", orderId);
        restTemplate.delete(url + "/" + orderId);
        return 1;
    }
}
