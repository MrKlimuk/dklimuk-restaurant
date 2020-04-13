package com.epam.brest.courses.service_rest;


import com.epam.brest.courses.model.Item;
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

import static com.epam.brest.courses.constants.ItemConstants.ITEM_NAME_SIZE;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
public class ItemServiceRestTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemServiceRest.class);

    public static final String URL = "http://localhost:8088/items";

    private final BigDecimal ITEM_PRICE = new BigDecimal(100);

    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    ItemServiceRest itemServiceRest;

    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        itemServiceRest = new ItemServiceRest(URL, restTemplate);
    }

    @Test
    public void shouldFindAllItems() throws Exception {

        LOGGER.debug("shouldFindAllItem()");
        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                );

        // when
        List<Item> items = itemServiceRest.findAllItem();

        // then
        mockServer.verify();
        assertNotNull(items);
        assertTrue(items.size() > 0);
    }

    @Test
    public void shouldFindItemById() throws Exception {

        // given
        Integer id = 1;
        Item item = new Item()
                .setItemId(id)
                .setItemName(RandomStringUtils.randomAlphabetic(ITEM_NAME_SIZE))
                .setItemPrice(ITEM_PRICE);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(item))
                );

        // when
        Optional<Item> itemOptional = itemServiceRest.findItemById(id);

        // then
        mockServer.verify();
        assertTrue(itemOptional.isPresent());
        assertEquals(itemOptional.get().getItemId(), id);
        assertEquals(itemOptional.get().getItemName(), item.getItemName());
        assertEquals(itemOptional.get().getItemPrice(), item.getItemPrice());
    }

    @Test
    public void shouldCreateItem() throws Exception {

        LOGGER.debug("shouldCreateItem()");
        // given
        Item item = new Item()
                .setItemName(RandomStringUtils.randomAlphabetic(ITEM_NAME_SIZE))
                .setItemPrice(ITEM_PRICE);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );
        // when
        Integer id = itemServiceRest.createItem(item);

        // then
        mockServer.verify();
        assertNotNull(id);
    }


    @Test
    public void shouldUpdateItem() throws Exception {

        // given
        Integer id = 1;
        Item item = new Item()
                .setItemId(id)
                .setItemName(RandomStringUtils.randomAlphabetic(ITEM_NAME_SIZE))
                .setItemPrice(ITEM_PRICE);

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
                        .body(mapper.writeValueAsString(item))
                );

        // when
        int result = itemServiceRest.updateItem(item);
        Optional<Item> updatedItemOptional = itemServiceRest.findItemById(id);

        // then
        mockServer.verify();
        assertTrue(1 == result);

        assertTrue(updatedItemOptional.isPresent());
        assertEquals(updatedItemOptional.get().getItemId(), id);
        assertEquals(updatedItemOptional.get().getItemName(), item.getItemName());
        assertEquals(updatedItemOptional.get().getItemPrice(), item.getItemPrice());
    }

    @Test
    public void shouldDeleteItem() throws Exception {

        // given
        Integer id = 1;
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL + "/" + id)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );
        // when
        int result = itemServiceRest.deleteItem(id);

        // then
        mockServer.verify();
        assertTrue(1 == result);
    }


    private Item create(int index) {
        Item item = new Item();
        item.setItemId(index);
        item.setItemName("nameItem" + index);
        item.setItemPrice(new BigDecimal(100));
        return item;
    }


}
