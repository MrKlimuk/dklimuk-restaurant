package com.epam.brest.courses.rest.exception;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(Integer id) {
        super("Order not found for id: " + id);
    }
}
