package com.epam.brest.courses.rest;

import com.epam.brest.courses.model.Position;
import com.epam.brest.courses.rest.exception.PositionNotFoundException;
import com.epam.brest.courses.service.PositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PositionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PositionController.class);

    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping(value = "/positions")
    public final List<Position> positions(){

        LOGGER.debug("positions()");

        return positionService.findAllPosition();
    }

    @GetMapping(value = "/positions/{id}")
    public Position findById(@PathVariable Integer id){

        LOGGER.debug("find position by id({})", id);

        return positionService.findPositionById(id).orElseThrow(() -> new PositionNotFoundException(id));
    }

    @PostMapping(path = "/positions", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> create(@RequestBody Position position) {

        LOGGER.debug("createPosition({})", position);
        Integer id = positionService.create(position);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping(value = "/positions", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updatePosition(@RequestBody Position position) {

        LOGGER.debug("updatePosition({})", position);
        int result = positionService.update(position);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/positions/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deletePosition(@PathVariable Integer id) {

        int result = positionService.delete(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
