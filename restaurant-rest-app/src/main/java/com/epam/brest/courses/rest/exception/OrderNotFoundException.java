package com.epam.brest.courses.rest.exception;

/**
 * Class for message "order not found".
 */
public class OrderNotFoundException extends RuntimeException{

    /**
     * Constructor accepts item id.
     * @param id
     */
    public OrderNotFoundException(Integer id) {
        super("Order not found for id: " + id);
    }
}
