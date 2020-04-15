package com.epam.brest.rest;

import com.epam.brest.courses.model.Position;
import com.epam.brest.courses.rest.PositionController;
import com.epam.brest.courses.rest.exception.CustomExceptionHandler;
import com.epam.brest.courses.rest.exception.ErrorResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.constants.PositionConstants.POSITION_NAME_SIZE;
import static com.epam.brest.courses.rest.exception.CustomExceptionHandler.POSITION_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
public class PositionControllerIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(PositionControllerIT.class);

    public static final String POSITIONS_ENDPOINT = "/positions";
    private final BigDecimal POSITION_PRICE = new BigDecimal(100);
    private final BigDecimal POSITION_PRICE_FOR_UPDATE = new BigDecimal(10);

    @Autowired
    private PositionController positionController;

    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    MockMvcPositionService mockMvcPositionService = new MockMvcPositionService();

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(positionController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(customExceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    public void shouldFindAllPositions() throws Exception {

        List<Position> positions = mockMvcPositionService.findAll();
        assertNotNull(positions);
        assertTrue(positions.size() > 0);
    }

    @Test
    public void shouldFindPositionById() throws Exception {

        // given
        Position position = new Position()
                .setPositionId(1)
                .setPositionOrderId(1)
                .setPositionName(RandomStringUtils.randomAlphabetic(POSITION_NAME_SIZE))
                .setPositionPrice(POSITION_PRICE)
                .setPositionCount(1);
        Integer id = mockMvcPositionService.create(position);

        assertNotNull(id);

        // when
        Optional<Position> optionalPosition = mockMvcPositionService.findById(id);

        // then
        assertTrue(optionalPosition.isPresent());
        assertEquals(optionalPosition.get().getPositionId(), id);
        assertEquals(optionalPosition.get().getPositionOrderId(), position.getPositionOrderId());
        assertEquals(optionalPosition.get().getPositionName(), position.getPositionName());
        assertEquals(optionalPosition.get().getPositionPrice(), position.getPositionPrice());
        assertEquals(optionalPosition.get().getPositionCount(), position.getPositionCount());
    }

    @Test
    public void shouldFindPositionByOrderId() throws Exception {

        List<Position> positions = mockMvcPositionService.findByOrderId(1);
        assertNotNull(positions);
        assertTrue(positions.size() > 0);
    }

    @Test
    public void shouldCreatePosition() throws Exception {
        Position position = new Position()
                .setPositionId(1)
                .setPositionOrderId(1)
                .setPositionName(RandomStringUtils.randomAlphabetic(POSITION_NAME_SIZE))
                .setPositionPrice(POSITION_PRICE)
                .setPositionCount(1);
        Integer id = mockMvcPositionService.create(position);

        assertNotNull(id);
    }

    @Test
    public void shouldUpdatePosition() throws Exception {

        // given
        Position position = new Position()
                .setPositionId(1)
                .setPositionOrderId(1)
                .setPositionName(RandomStringUtils.randomAlphabetic(POSITION_NAME_SIZE))
                .setPositionPrice(POSITION_PRICE)
                .setPositionCount(1);
        Integer id = mockMvcPositionService.create(position);

        assertNotNull(id);

        Optional<Position> positionOptional = mockMvcPositionService.findById(id);
        assertTrue(positionOptional.isPresent());

        positionOptional.get()
                .setPositionName(RandomStringUtils.randomAlphabetic(POSITION_NAME_SIZE))
                .setPositionPrice(POSITION_PRICE_FOR_UPDATE)
                .setPositionCount(2);
        // when
        int result = mockMvcPositionService.update(positionOptional.get());

        // then
        assertTrue(1 == result);

        Optional<Position> updatedPositionOptional = mockMvcPositionService.findById(id);
        assertTrue(updatedPositionOptional.isPresent());
        assertEquals(updatedPositionOptional.get().getPositionName(), positionOptional.get().getPositionName());
        assertEquals(updatedPositionOptional.get().getPositionPrice(), positionOptional.get().getPositionPrice());
        assertEquals(updatedPositionOptional.get().getPositionCount(), positionOptional.get().getPositionCount());

    }

    @Test
    public void shouldDeletePosition() throws Exception {
        // given
        Position position = new Position()
                .setPositionId(1)
                .setPositionOrderId(1)
                .setPositionName(RandomStringUtils.randomAlphabetic(POSITION_NAME_SIZE))
                .setPositionPrice(POSITION_PRICE)
                .setPositionCount(1);
        Integer id = mockMvcPositionService.create(position);

        assertNotNull(id);

        List<Position> positions= mockMvcPositionService.findAll();
        assertNotNull(positions);

        // when
        int result = mockMvcPositionService.delete(id);

        // then
        assertTrue(1 == result);

        List<Position> currentPosition = mockMvcPositionService.findAll();
        assertNotNull(currentPosition);

        assertTrue(positions.size()-1 == currentPosition.size());
    }

    @Test
    public void shouldReturnPositionNotFoundError() throws Exception {

        LOGGER.debug("shouldReturnPositionNotFoundError()");
        MockHttpServletResponse response =
                mockMvc.perform(MockMvcRequestBuilders.get(POSITIONS_ENDPOINT + "/999999")
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound())
                        .andReturn().getResponse();
        assertNotNull(response);
        ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        assertNotNull(errorResponse);
        assertEquals(errorResponse.getMessage(), POSITION_NOT_FOUND);
    }

    class MockMvcPositionService {

        public List<Position> findAll() throws Exception {
            LOGGER.debug("findAll()");
            MockHttpServletResponse response = mockMvc.perform(get(POSITIONS_ENDPOINT)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Position>>() {});
        }

        public Optional<Position> findById(Integer positionId) throws Exception {
            LOGGER.debug("findById({})", positionId);
            MockHttpServletResponse response = mockMvc.perform(get(POSITIONS_ENDPOINT + "/" + positionId)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return Optional.of(objectMapper.readValue(response.getContentAsString(), Position.class));

        }
        public List<Position> findByOrderId(Integer orderId) throws Exception {
            LOGGER.debug("findByOrderId({})", orderId);
            MockHttpServletResponse response = mockMvc.perform(get(POSITIONS_ENDPOINT + "/orderId/" + orderId)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Position>>() {});

        }

        public Integer create(Position position) throws Exception {
            LOGGER.debug("create({})", position);
            String json = objectMapper.writeValueAsString(position);
            MockHttpServletResponse response =
                    mockMvc.perform(post(POSITIONS_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        public int update(Position position) throws Exception {

            LOGGER.debug("position({})", position);
            MockHttpServletResponse response =
                    mockMvc.perform(put(POSITIONS_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(position))
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        public int delete(Integer positionId) throws Exception {

            LOGGER.debug("delete(id:{})", positionId);
            MockHttpServletResponse response = mockMvc.perform(
                    MockMvcRequestBuilders.delete(new StringBuilder(POSITIONS_ENDPOINT).append("/")
                            .append(positionId).toString())
                            .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();

            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }
    }




}
