package com.epam.brest.courses.rest.exception;

public class PositionNotFoundException extends RuntimeException{

    public PositionNotFoundException(Integer id){
        super("Position not found for id:" + id);
    }
}
