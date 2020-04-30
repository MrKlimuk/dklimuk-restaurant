package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.ItemDao;
import com.epam.brest.courses.model.Item;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * A class that defines a set of operations
 * with an item model.
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService{

    /**
     * A item data access object.
     */
    private final ItemDao itemDao;

    /**
     * Constructor accepts dao layer object.
     * @param itemDao
     */
    public ItemServiceImpl(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    /**
     * Find all items.
     *
     * @return
     */
    @Override
    public List<Item> findAllItem() {
        return itemDao.findAll();
    }

    /**
     * Find item by Id.
     *
     * @param itemId item id.
     * @return item.
     */
    @Override
    public Optional<Item> findItemById(Integer itemId) {
        return itemDao.findById(itemId);
    }

    /**
     * Creates new item.
     *
     * @param item item.
     * @return created item id.
     */
    @Override
    public Integer createItem(Item item) {
        return itemDao.create(item);
    }

    /**
     * Update item.
     *
     * @param item item.
     * @return number of updated records in the database.
     */
    @Override
    public int updateItem(Item item) {
        return itemDao.update(item);
    }

    /**
     * Delete item.
     *
     * @param itemId item Id.
     * @return the number of rows affected.
     */
    @Override
    public int deleteItem(Integer itemId) {
        return itemDao.delete(itemId);
    }
}
