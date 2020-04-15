package com.epam.brest.courses.service_rest;


import com.epam.brest.courses.model.Position;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.constants.PositionConstants.POSITION_NAME_SIZE;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
public class PositionServiceRestTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PositionServiceRest.class);

    public static final String URL = "http://localhost:8088/positions";

    private final BigDecimal POSITION_PRICE = new BigDecimal(100);

    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    PositionServiceRest positionServiceRest;

    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        positionServiceRest = new PositionServiceRest(URL, restTemplate);
    }

    @Test
    public void shouldFindAllPosition() throws Exception {

        LOGGER.debug("shouldFindAllPosition()");
        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                );

        // when
        List<Position> positions = positionServiceRest.findAllPosition();

        // then
        mockServer.verify();
        assertNotNull(positions);
        assertTrue(positions.size() > 0);
    }


    @Test
    public void shouldFindPositionById() throws Exception {

        LOGGER.debug("shouldFindPositionById()");

        // given
        Integer id = 1;
        Position position = new Position()
                .setPositionId(id)
                .setPositionOrderId(1)
                .setPositionName(RandomStringUtils.randomAlphabetic(POSITION_NAME_SIZE))
                .setPositionPrice(POSITION_PRICE)
                .setPositionCount(1);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(position))
                );

        // when
        Optional<Position> optionalPosition = positionServiceRest.findPositionById(id);

        // then
        mockServer.verify();
        assertTrue(optionalPosition.isPresent());
        assertEquals(optionalPosition.get().getPositionId(), id);
        assertEquals(optionalPosition.get().getPositionOrderId(), position.getPositionOrderId());
        assertEquals(optionalPosition.get().getPositionName(), position.getPositionName());
        assertEquals(optionalPosition.get().getPositionPrice(), position.getPositionPrice());
        assertEquals(optionalPosition.get().getPositionCount(), position.getPositionCount());
    }

    @Test
    public void shouldFindPositionByOrderId() throws Exception {
        LOGGER.debug("shouldFindPositionByOrderId");
        // given
        Integer orderId = 1;

        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL + "/orderId/" + orderId)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                );
        // when
        List<Position> positions = positionServiceRest.findPositionByOrderId(orderId);

        // then
        mockServer.verify();
        assertNotNull(positions);
        assertTrue(positions.size() > 0);

    }

    @Test
    public void shouldCreatePosition() throws Exception {

        LOGGER.debug("shouldCreatePosition()");
        // given
        Position position = new Position()
                .setPositionId(1)
                .setPositionOrderId(1)
                .setPositionName(RandomStringUtils.randomAlphabetic(POSITION_NAME_SIZE))
                .setPositionPrice(POSITION_PRICE)
                .setPositionCount(1);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );
        // when
        Integer id = positionServiceRest.create(position);

        // then
        mockServer.verify();
        assertNotNull(id);
    }

    @Test
    public void shouldUpdatePosition() throws Exception {

        // given
        Integer id = 1;
        Position position = new Position()
                .setPositionId(id)
                .setPositionOrderId(1)
                .setPositionName(RandomStringUtils.randomAlphabetic(POSITION_NAME_SIZE))
                .setPositionPrice(POSITION_PRICE)
                .setPositionCount(1);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(position))
                );

        // when
        int result = positionServiceRest.update(position);
        Optional<Position> updatedPositionOptional = positionServiceRest.findPositionById(id);

        // then
        mockServer.verify();
        assertTrue(1 == result);

        assertTrue(updatedPositionOptional.isPresent());
        assertEquals(updatedPositionOptional.get().getPositionName(), position.getPositionName());
        assertEquals(updatedPositionOptional.get().getPositionPrice(), position.getPositionPrice());
        assertEquals(updatedPositionOptional.get().getPositionCount(), position.getPositionCount());
    }

    @Test
    public void shouldDeletePosition() throws Exception {

        // given
        Integer id = 1;
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL + "/" + id)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );
        // when
        int result = positionServiceRest.delete(id);

        // then
        mockServer.verify();
        assertTrue(1 == result);
    }

    private Position create(int index) {
        Position position = new Position();
        position.setPositionId(index);
        position.setPositionOrderId(index + index);
        position.setPositionName("namePosition" + index);
        position.setPositionPrice(new BigDecimal(100));
        position.setPositionCount(index);
        return position;
    }
}
