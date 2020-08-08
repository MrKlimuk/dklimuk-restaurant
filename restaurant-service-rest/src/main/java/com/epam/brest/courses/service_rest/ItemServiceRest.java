package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.Item;
import com.epam.brest.courses.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

/**
 * Gets data from rest in JSON format.
 */
public class ItemServiceRest implements ItemService {

    /**
     * Logger for ItemServiceRest.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemServiceRest.class);

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
    public ItemServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    /**
     * Find all items.
     *
     * @return item list.
     */
    @Override
    public List<Item> findAllItem() {

        LOGGER.debug("findAll()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<Item>) responseEntity.getBody();
    }

    @Override
    public List<Item> findAllItemPage(int pageNumber, int pageSize) {

        ResponseEntity responseEntity = restTemplate.getForEntity(url + "Page?pageNumber=" + pageNumber
                + "&pageSize=" + pageSize, List.class);

        return (List<Item>) responseEntity.getBody();
    }



    /**
     * Find item by Id.
     *
     * @param itemId item Id.
     * @return item.
     */
    @Override
    public Optional<Item> findItemById(Integer itemId) {

        LOGGER.debug("findItemById()");
        ResponseEntity<Item> responseEntity =
                restTemplate.getForEntity(url + "/" + itemId, Item.class);
        return Optional.ofNullable(responseEntity.getBody());
    }

    @Override
    public Integer getItemTotalPages(int pageNumber, int pageSize) {
//        Map<String, Integer> param = new HashMap<String, Integer>();
//        param.put("pageNumber", pageSize);
//        param.put("pageSize", pageSize);
        ResponseEntity responseEntity = restTemplate.getForEntity(url + "TotalPages?pageNumber=" + pageNumber
                + "&pageSize=" + pageSize, Integer.class);

        return (Integer) responseEntity.getBody();
    }


    /**
     * Creates new item.
     *
     * @param item item.
     * @return created item id.
     */
    @Override
    public Integer createItem(Item item) {
        LOGGER.debug("create({})", item);
        ResponseEntity responseEntity = restTemplate.postForEntity(url, item, Integer.class);
        Object result = responseEntity.getBody();
        return (Integer) result;
    }

    @Override
    public Integer generateItem(int number, String language) {

        Map<String, String> param = new HashMap<String, String>();
        param.put("number", String.valueOf(number));
        param.put("language", String.valueOf(language));
        restTemplate.getForEntity(url + "/generate?number=" + number
                                            + "&language=" + language, List.class);
        return 1;
    }

    /**
     * Update item.
     *
     * @param item item.
     * @return number of updated records in the database.
     */
    @Override
    public int updateItem(Item item) {

        LOGGER.debug("update({})", item);
        restTemplate.put(url, item);
        return 1;
    }

    /**
     * Delete item.
     *
     * @param itemId item Id.
     * @return the number of rows affected.
     */
    @Override
    public int deleteItem(Integer itemId) {

        LOGGER.debug("delete({})", itemId);
        restTemplate.delete(url + "/" + itemId);
        return 1;
    }


    @Override
    public void deleteAllItems() {

        LOGGER.info("deleteAll({})");
        restTemplate.optionsForAllow(url + "/delete");
    }
}
