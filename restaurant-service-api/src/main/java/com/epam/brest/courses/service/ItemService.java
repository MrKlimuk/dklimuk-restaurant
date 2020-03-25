package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    List<Item> findAllItem();
    Optional<Item> findItemById(Integer itemId);
    Integer createItem(Item item);
    int updateItem(Item item);
    int deleteItem(Integer itemId);
}
