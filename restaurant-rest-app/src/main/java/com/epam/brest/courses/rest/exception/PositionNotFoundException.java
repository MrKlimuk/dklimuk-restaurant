package com.epam.brest.courses.rest.exception;

/**
 * Class for message "position not found".
 */
public class PositionNotFoundException extends RuntimeException{

    /**
     * Constructor accepts position id.
     * @param id
     */
    public PositionNotFoundException(Integer id){
        super("Position not found for id:" + id);
    }
}
