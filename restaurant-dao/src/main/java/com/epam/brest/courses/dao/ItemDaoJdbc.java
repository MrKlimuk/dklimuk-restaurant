package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Item;

import java.util.List;
import java.util.Optional;

public class ItemDaoJdbc implements ItemDao {
    @Override
    public List<Item> findAll() {
        return null;
    }

    @Override
    public Optional<Item> findById(Integer itemId) {
        return Optional.empty();
    }

    @Override
    public Integer create(Item item) {
        return null;
    }

    @Override
    public int update(Item item) {
        return 0;
    }

    @Override
    public int delete(Integer itemId) {
        return 0;
    }
}
