package com.epam.brest.courses.dao;



import com.epam.brest.courses.model.Item;

import java.util.List;
import java.util.Optional;


/**
 * This DAO interface is designed to manage the item database.
 */
public interface ItemDao {

    /**
     * Find all items.
     *
     * @return item list.
     */
    List<Item> findAll();

    /**
     * Find item by Id.
     *
     * @param itemId item Id.
     * @return item.
     */
    Optional<Item> findById(Integer itemId);

    /**
     * Creates new item.
     *
     * @param item item.
     * @return created item id.
     */
    Integer create(Item item);

    /**
     * Update item.
     *
     * @param item item.
     * @return number of updated records in the database.
     */
    int update(Item item);

    /**
     * Delete item.
     *
     * @param itemId item Id.
     * @return the number of rows affected.
     */
    int delete(Integer itemId);

}
