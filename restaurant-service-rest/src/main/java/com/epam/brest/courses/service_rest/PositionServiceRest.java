package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.Position;
import com.epam.brest.courses.service.PositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

/**
 * This DAO interface is designed to manage the position database.
 */
public class PositionServiceRest implements PositionService {

    /**
     * Logger for PositionServiceRest.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PositionServiceRest.class);

    /**
     * URL rest-app.
     */
    private String url;

    /**
     * Client to perform HTTP requests.
     */
    private RestTemplate restTemplate;

    /**
     * Constructor accepts URL and restTemplate.
     *
     * @param url url.
     * @param restTemplate rest template.
     */
    public PositionServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    /**
     * Find all orders.
     *
     * @return position list.
     */
    @Override
    public List<Position> findAllPosition() {
        LOGGER.debug("findAll()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<Position>) responseEntity.getBody();
    }

    /**
     * Find position by id.
     *
     * @param positionId position id.
     * @return position.
     */
    @Override
    public Optional<Position> findPositionById(Integer positionId) {
        LOGGER.debug("findPositionById({})", positionId);
        ResponseEntity<Position> responseEntity =
                restTemplate.getForEntity(url + "/" + positionId, Position.class);
        return Optional.ofNullable(responseEntity.getBody());
    }

    /**
     * Find positions by order id.
     *
     * @param positionOrderId position order id.
     * @return positions list.
     */
    @Override
    public List<Position> findPositionByOrderId(Integer positionOrderId) {
        LOGGER.debug("findPositionByOrderId({})", positionOrderId);
        ResponseEntity responseEntity =
                restTemplate.getForEntity(url + "/orderId/" + positionOrderId, List.class);
        return (List<Position>) responseEntity.getBody();
    }

    /**
     * Create position.
     *
     * @param position position.
     * @return created position id.
     */
    @Override
    public Integer create(Position position) {
        LOGGER.debug("create({})", position);
        ResponseEntity responseEntity = restTemplate.postForEntity(url, position, Integer.class);
        Object result = responseEntity.getBody();
        return (Integer) result;
    }

    /**
     * Update position.
     *
     * @param position position.
     * @return number of updated records in the database.
     */
    @Override
    public int update(Position position) {

        LOGGER.debug("update({})", position);
        restTemplate.put(url, position);
        return 1;
    }

    /**
     * Delete position by Id.
     *
     * @param positionId position Id.
     * @return the number of rows affected.
     */
    @Override
    public int delete(Integer positionId) {
        LOGGER.debug("delete({})", positionId);
        restTemplate.delete(url + "/" + positionId);
        return 1;
    }
}
