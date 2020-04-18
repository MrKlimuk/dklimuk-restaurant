package com.epam.brest.rest;


import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.rest.OrderController;
import com.epam.brest.courses.rest.exception.CustomExceptionHandler;
import com.epam.brest.courses.rest.exception.ErrorResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.constants.OrderConstants.ORDER_NAME_SIZE;
import static com.epam.brest.courses.rest.exception.CustomExceptionHandler.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
public class OrderControllerIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderControllerIT.class);

    public static final String ORDERS_ENDPOINT = "/orders";

    private final BigDecimal ORDER_PRICE = new BigDecimal(100);
    private final BigDecimal ORDER_PRICE_FOR_UPDATE = new BigDecimal(10);
    private final LocalDate DATE = LocalDate.of(2020, 4, 18);

    @Autowired
    private OrderController orderController;

    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    MockMvcOrderService mockMvcOrderService = new MockMvcOrderService();

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(customExceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    public void shouldFindAllOrders() throws Exception {

        List<Order> orders = mockMvcOrderService.findAll();
        assertNotNull(orders);
        assertTrue(orders.size() > 0);
    }

    @Test
    public void shouldFindOrderById() throws Exception {

        // givenq
        Order order = new Order()
                .setOrderName(RandomStringUtils.randomAlphabetic(ORDER_NAME_SIZE))
                .setOrderPrice(new BigDecimal(0))
                .setOrderDate(DATE);
        Integer id = mockMvcOrderService.create(order);

        assertNotNull(id);

        // when
        Optional<Order> optionalOrder = mockMvcOrderService.findById(id);

        // then
        assertTrue(optionalOrder.isPresent());
        assertEquals(optionalOrder.get().getOrderId(), id);
        assertEquals(optionalOrder.get().getOrderName(), order.getOrderName());
        assertEquals(optionalOrder.get().getOrderPrice(), order.getOrderPrice());
    }

    @Test
    public void shouldCreateOrder() throws Exception {
        Order order = new Order()
                .setOrderName(RandomStringUtils.randomAlphabetic(ORDER_NAME_SIZE))
                .setOrderDate(DATE);

        Integer id = mockMvcOrderService.create(order);
        assertNotNull(id);
    }

    @Test
    public void shouldUpdateOrder() throws Exception {

        // given
        Order order = new Order()
                .setOrderName(RandomStringUtils.randomAlphabetic(ORDER_NAME_SIZE))
                .setOrderDate(DATE);

        Integer id = mockMvcOrderService.create(order);
        assertNotNull(id);

        Optional<Order> orderOptional = mockMvcOrderService.findById(id);
        assertTrue(orderOptional.isPresent());

        orderOptional.get().
                setOrderName(RandomStringUtils.randomAlphabetic(ORDER_NAME_SIZE));

        // when
        int result = mockMvcOrderService.update(orderOptional.get());

        // then
        assertTrue(1 == result);

        Optional<Order> updatedOrderOptional = mockMvcOrderService.findById(id);
        assertTrue(updatedOrderOptional.isPresent());
        assertEquals(updatedOrderOptional.get().getOrderId(), id);
        assertEquals(updatedOrderOptional.get().getOrderName(),orderOptional.get().getOrderName());
    }

    @Test
    public void shouldDeleteOrder() throws Exception {
        // given
        Order order = new Order()
                .setOrderName(RandomStringUtils.randomAlphabetic(ORDER_NAME_SIZE))
                .setOrderDate(DATE);

        Integer id = mockMvcOrderService.create(order);
        assertNotNull(id);

        List<Order> orders = mockMvcOrderService.findAll();
        assertNotNull(order);

        // when
        int result = mockMvcOrderService.delete(id);

        // then
        assertTrue(1 == result);

        List<Order> currentOrder = mockMvcOrderService.findAll();
        assertNotNull(currentOrder);

        assertTrue(orders.size()-1 == currentOrder.size());
    }

    @Test
    public void shouldReturnOrderNotFoundError() throws Exception {

        LOGGER.debug("shouldReturnOrderNotFoundError()");
        MockHttpServletResponse response =
                mockMvc.perform(MockMvcRequestBuilders.get(ORDERS_ENDPOINT + "/999999")
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound())
                        .andReturn().getResponse();
        assertNotNull(response);
        ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        assertNotNull(errorResponse);
        assertEquals(errorResponse.getMessage(), ORDER_NOT_FOUND);
    }

    @Test
    public void shouldFailOnCreateOrderWithDuplicateName() throws Exception {
        Order order1 = new Order()
                .setOrderName(RandomStringUtils.randomAlphabetic(ORDER_NAME_SIZE))
                .setOrderDate(DATE);

        Integer id = mockMvcOrderService.create(order1);
        assertNotNull(id);

        Order order2 = new Order()
                .setOrderName(order1.getOrderName());

        MockHttpServletResponse response =
                mockMvc.perform(post(ORDERS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order2))
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isUnprocessableEntity())
                        .andReturn().getResponse();

        assertNotNull(response);
        ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        assertNotNull(errorResponse);
        assertEquals(errorResponse.getMessage(), VALIDATION_ERROR);
    }





    class MockMvcOrderService {

        public List<Order> findAll() throws Exception {
            LOGGER.debug("findAll()");
            MockHttpServletResponse response = mockMvc.perform(get(ORDERS_ENDPOINT)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Order>>() {});
        }

        public Optional<Order> findById(Integer orderId) throws Exception {

            LOGGER.debug("findById({})", orderId);
            MockHttpServletResponse response = mockMvc.perform(get(ORDERS_ENDPOINT + "/" + orderId)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return Optional.of(objectMapper.readValue(response.getContentAsString(), Order.class));
        }

        public Integer create(Order order) throws Exception {

            LOGGER.debug("create({})", order);
            String json = objectMapper.writeValueAsString(order);
            MockHttpServletResponse response =
                    mockMvc.perform(post(ORDERS_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        private int update(Order order) throws Exception {

            LOGGER.debug("update({})", order);
            MockHttpServletResponse response =
                    mockMvc.perform(put(ORDERS_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(order))
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        private int delete(Integer orderId) throws Exception {

            LOGGER.debug("delete(id:{})", orderId);
            MockHttpServletResponse response = mockMvc.perform(
                    MockMvcRequestBuilders.delete(new StringBuilder(ORDERS_ENDPOINT).append("/")
                            .append(orderId).toString())
                            .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();

            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }
    }
}
