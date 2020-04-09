package com.epam.brest.courses.rest.exception;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(Integer id){
        super("Item not found for id:" + id);
    }
}
