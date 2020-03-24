package com.epam.brest.courses.dao;



import com.epam.brest.courses.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemDao {

    List<Item> findAll();
    Optional<Item> findById(Integer itemId);
    Integer create(Item item);
    int update(Item item);
    int delete(Integer itemId);

}
