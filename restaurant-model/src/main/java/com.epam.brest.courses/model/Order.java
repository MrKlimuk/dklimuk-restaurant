package com.epam.brest.courses.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Database order model.
 */
public class Order {

    /**
     * Order id.
     */
    private Integer orderId;

    /**
     * Order name.
     */
    private String orderName;

    /**
     * Order price.
     */
    private BigDecimal orderPrice;

    /**
     * Order date.
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate orderDate;


    /**
     * Get order id.
     * @return orderId.
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * Set order id.
     * @param orderId
     * @return orderId.
     */
    public Order setOrderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    /**
     * Get order name.
     * @return orderName.
     */
    public String getOrderName() {
        return orderName;
    }

    /**
     * Set order name.
     * @param orderName
     * @return orderName
     */
    public Order setOrderName(String orderName) {
        this.orderName = orderName;
        return this;
    }

    /**
     * Get order price.
     * @return order price.
     */
    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    /**
     * Set order price.
     * @param orderPrice
     * @return orderPrice.
     */
    public Order setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
        return this;
    }

    /**
     * Get order date.
     * @return orderDate.
     */
    public LocalDate getOrderDate() {
        return orderDate;
    }

    /**
     * Set order date.
     * @param orderDate
     * @return orderDate.
     */
    public Order setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    /**
     * Represents order field values as a string.
     * @return a string order field values.
     */
    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderName='" + orderName + '\'' +
                ", orderPrice=" + orderPrice +
                ", orderDate=" + orderDate +
                '}';
    }
}
