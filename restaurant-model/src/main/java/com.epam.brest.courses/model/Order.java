package com.epam.brest.courses.model;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;



@Entity
@Table(name = "ordertable")
public class Order {


    /**
     * Order id.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    /**
     * Order name.
     */
    @Column(name = "order_name")
    private String orderName;

    /**
     * Order price.
     */
    @Column(name = "order_price")
    private BigDecimal orderPrice;

    /**
     * Order date.
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "order_date")
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
