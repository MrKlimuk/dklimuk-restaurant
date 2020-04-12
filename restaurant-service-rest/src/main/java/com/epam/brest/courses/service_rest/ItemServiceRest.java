package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.Item;
import com.epam.brest.courses.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

public class ItemServiceRest implements ItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public ItemServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Item> findAllItem() {

        LOGGER.debug("findAll()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<Item>) responseEntity.getBody();
    }

    @Override
    public Optional<Item> findItemById(Integer itemId) {

        LOGGER.debug("findItemById()");
        ResponseEntity<Item> responseEntity =
                restTemplate.getForEntity(url + "/" + itemId, Item.class);
        return Optional.ofNullable(responseEntity.getBody());
    }

    @Override
    public Integer createItem(Item item) {
        LOGGER.debug("create({})", item);
        ResponseEntity responseEntity = restTemplate.postForEntity(url, item, Integer.class);
        Object result = responseEntity.getBody();
        return (Integer) result;
    }

    @Override
    public int updateItem(Item item) {

        LOGGER.debug("update({})", item);
        restTemplate.put(url, item);
        return 1;
    }

    @Override
    public int deleteItem(Integer itemId) {

        LOGGER.debug("delete({})", itemId);
        restTemplate.delete(url + "/" + itemId);
        return 1;
    }
}
