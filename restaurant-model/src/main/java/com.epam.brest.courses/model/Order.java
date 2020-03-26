package com.epam.brest.courses.model;

import java.math.BigDecimal;

public class Order {
    private Integer orderId;
    private String orderName;
    private BigDecimal orderPrice;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public Order setOrderName(String orderName) {
        this.orderName = orderName;
        return this;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderName='" + orderName + '\'' +
                ", orderPrice=" + orderPrice +
                '}';
    }
}
