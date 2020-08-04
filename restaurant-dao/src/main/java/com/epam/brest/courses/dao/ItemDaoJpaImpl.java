package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;



@Profile("jpa")
public class ItemDaoJpaImpl implements ItemDao {


    private static final Logger LOGGER = LoggerFactory.getLogger(ItemDaoJpaImpl.class);

    @Autowired
    ItemDaoJpa itemDaoJpa;


    private static final String CHECK_COUNT_NAME = "select count(item_id) from item where lower(item_name) = lower(:itemName)";


    @Override
    public List<Item> findAllItems() {

      return (List<Item>) itemDaoJpa.findAll();
    }

    @Override
    public Page<Item> findAllPage(int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Item> itemPage = itemDaoJpa.findAll(pageable);
        return itemPage;
    }


    @Override
    public Optional<Item> findItemById(Integer itemId) {

        return itemDaoJpa.findById(itemId);

    }

    @Override
    public Integer create(Item item) {
        LOGGER.info("first DAO item({})", item);
        itemDaoJpa.save(item);
        return item.getItemId();
    }


    @Override
    public int update(Item item) {
        itemDaoJpa.save(item);
        return 0;
    }

    @Override
    public int delete(Integer itemId) {
        itemDaoJpa.deleteById(itemId);
        return 1;
    }

    @Override
    public void deleteAllItems() {
        itemDaoJpa.deleteAll();
    }
}
