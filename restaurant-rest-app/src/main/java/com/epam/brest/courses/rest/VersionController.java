package com.epam.brest.courses.rest;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Rest controller for version.
 */
@RestController
public class VersionController {

    /**
     * Version variable.
     */
    private final static String VERSION = "0.0.1";

    /**
     * Version controller.
     *
     * @return version.
     */
    @GetMapping(value = "/version")
    public String version(){
        return VERSION;
    }
}
