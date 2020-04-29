package com.epam.brest.courses.rest.exception;

/**
 * Class for message "item not found".
 */
public class ItemNotFoundException extends RuntimeException {

    /**
     * Constructor accepts item id.
     * @param id
     */
    public ItemNotFoundException(Integer id){
        super("Item not found for id:" + id);
    }
}
