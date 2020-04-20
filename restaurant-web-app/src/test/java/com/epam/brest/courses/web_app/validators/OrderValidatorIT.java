package com.epam.brest.courses.web_app.validators;

import com.epam.brest.courses.model.Order;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.thymeleaf.util.StringUtils;

import java.math.BigDecimal;

import static com.epam.brest.courses.constants.OrderConstants.ORDER_NAME_SIZE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderValidatorIT {

    private Order order;

    private OrderValidator orderValidator = new OrderValidator();
    private BindingResult result;

    @BeforeEach
    void setup(){
        order = Mockito.mock(Order.class);
        result = new BeanPropertyBindingResult(order, "order");
    }

    @Test
    void shouldRejectEmptyOrderName() {
        // given
        Mockito.when(order.getOrderName()).thenReturn("");
        Mockito.when(order.getOrderPrice()).thenReturn(new BigDecimal(1));

        // when
        orderValidator.validate(order, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectNullOrderName(){
        // given
        Mockito.when(order.getOrderName()).thenReturn(null);
        Mockito.when(order.getOrderPrice()).thenReturn(new BigDecimal(1));

        // when
        orderValidator.validate(order, result);

        // then
        assertTrue(result.hasErrors());

    }

    @Test
    void shouldRejectLargeOrderName() {

        // given
        String filled = StringUtils.repeat("*", ORDER_NAME_SIZE + 1);
        Mockito.when(order.getOrderName()).thenReturn(filled);
        Mockito.when(order.getOrderPrice()).thenReturn(new BigDecimal(1));

        // when
        orderValidator.validate(order, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldValidateOrderName() {

        // given
        String filled = StringUtils.repeat("*", ORDER_NAME_SIZE);
        Mockito.when(order.getOrderName()).thenReturn(filled);
        Mockito.when(order.getOrderPrice()).thenReturn(new BigDecimal(1));

        // when
        orderValidator.validate(order, result);

        // then
        assertFalse(result.hasErrors());
    }

    @Test
    void shouldRejectOrderPriceLessThenZero() {
        // given
        Mockito.when(order.getOrderName()).thenReturn(RandomStringUtils.randomAlphabetic(ORDER_NAME_SIZE));
        Mockito.when(order.getOrderPrice()).thenReturn(new BigDecimal(-1));

        // when
        orderValidator.validate(order, result);

        // then
        assertTrue(result.hasErrors());
    }
}
