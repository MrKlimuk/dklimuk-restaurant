package com.epam.brest.courses.rest.exception;

import java.util.Arrays;
import java.util.List;

/**
 * Class for representation of response errors.
 */
public class ErrorResponse {

    /**
     * Default constructor.
     */
    public ErrorResponse(){
        super();
    }

    /**
     * Constructor accepts error message and list details.
     * @param message
     * @param details
     */
    public ErrorResponse(String message, List<String> details){
        super();
        this.message = message;
        this.details = details;
    }

    /**
     * Error message.
     */
    private String message;

    /**
     * Errors details.
     */
    private List<String> details;

    /**
     * Constructor accepts error message and details.
     * @param message
     * @param ex
     */
    public ErrorResponse(String message, Exception ex){
        super();
        this.message = message;
        if (ex != null){
            this.details = Arrays.asList(ex.getMessage());
        }
    }

    /**
     * Get message.
     * @return message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set message.
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get details.
     * @return details.
     */
    public List<String> getDetails() {
        return details;
    }

    /**
     * Set details.
     * @param details
     */
    public void setDetails(List<String> details) {
        this.details = details;
    }
}
