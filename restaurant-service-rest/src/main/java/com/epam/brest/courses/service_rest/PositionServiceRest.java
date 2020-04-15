package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.Position;
import com.epam.brest.courses.service.PositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

public class PositionServiceRest implements PositionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PositionServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public PositionServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Position> findAllPosition() {
        LOGGER.debug("findAll()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<Position>) responseEntity.getBody();
    }

    @Override
    public Optional<Position> findPositionById(Integer positionId) {
        LOGGER.debug("findPositionById({})", positionId);
        ResponseEntity<Position> responseEntity =
                restTemplate.getForEntity(url + "/" + positionId, Position.class);
        return Optional.ofNullable(responseEntity.getBody());
    }

    @Override
    public List<Position> findPositionByOrderId(Integer positionOrderId) {
        LOGGER.debug("findPositionByOrderId({})", positionOrderId);
        ResponseEntity responseEntity =
                restTemplate.getForEntity(url + "/orderId/" + positionOrderId, List.class);
        return (List<Position>) responseEntity.getBody();
    }

    @Override
    public Integer create(Position position) {
        LOGGER.debug("create({})", position);
        ResponseEntity responseEntity = restTemplate.postForEntity(url, position, Integer.class);
        Object result = responseEntity.getBody();
        return (Integer) result;
    }

    @Override
    public int update(Position position) {

        LOGGER.debug("update({})", position);
        restTemplate.put(url, position);
        return 1;
    }

    @Override
    public int delete(Integer positionId) {
        LOGGER.debug("delete({})", positionId);
        restTemplate.delete(url + "/" + positionId);
        return 1;
    }
}
