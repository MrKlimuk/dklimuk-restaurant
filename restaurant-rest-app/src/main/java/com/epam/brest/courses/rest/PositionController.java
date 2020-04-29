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

/**
 * Rest controller for position.
 */
@RestController
public class PositionController {

    /**
     * Default logger for position class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PositionController.class);

    /**
     * Object for communication with the position level
     */
    private final PositionService positionService;

    /**
     * Constructor accepts service layer object.
     *
     * @param positionService
     */
    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    /**
     * Find all positions.
     *
      * @return positions list.
     */
    @GetMapping(value = "/positions")
    public final List<Position> positions(){

        LOGGER.debug("positions()");

        return positionService.findAllPosition();
    }

    /**
     * Find position by id.
     *
     * @param id position id.
     * @return position.
     */
    @GetMapping(value = "/positions/{id}")
    public Position findById(@PathVariable Integer id){

        LOGGER.debug("find position by id({})", id);

        return positionService.findPositionById(id).orElseThrow(() -> new PositionNotFoundException(id));
    }

    /**
     * Find positions by order id.
     *
     * @param orderId position order id.
     * @return positions list.
     */
    @GetMapping(value = "/positions/orderId/{orderId}")
    public List<Position> findPositionByOrderId(@PathVariable Integer orderId){

        LOGGER.debug("find position by order id({})", orderId);

        return positionService.findPositionByOrderId(orderId);
    }

    /**
     *  Add new position to database.
     * @param position
     * @return
     */
    @PostMapping(path = "/positions", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> create(@RequestBody Position position) {

        LOGGER.debug("createPosition({})", position);
        Integer id = positionService.create(position);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    /**
     * Update position.
     *
     * @param position position.
     * @return number of updated records in the database.
     */
    @PutMapping(value = "/positions", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updatePosition(@RequestBody Position position) {

        LOGGER.debug("updatePosition({})", position);
        int result = positionService.update(position);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * Delete position by Id.
     *
     * @param id position Id.
     * @return the number of rows affected.
     */
    @DeleteMapping(value = "/positions/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deletePosition(@PathVariable Integer id) {

        int result = positionService.delete(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
