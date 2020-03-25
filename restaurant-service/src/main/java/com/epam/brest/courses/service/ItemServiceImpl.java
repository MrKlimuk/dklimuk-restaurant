package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.ItemDao;
import com.epam.brest.courses.model.Item;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ItemServiceImpl implements ItemService{

    private final ItemDao itemDao;

    public ItemServiceImpl(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Override
    public List<Item> findAllItem() {
        return itemDao.findAll();
    }

    @Override
    public Optional<Item> findItemById(Integer itemId) {
        return itemDao.findById(itemId);
    }

    @Override
    public Integer createItem(Item item) {
        return itemDao.create(item);
    }


    @Override
    public int updateItem(Item item) {
        return itemDao.update(item);
    }

    @Override
    public int deleteItem(Integer itemId) {
        return itemDao.delete(itemId);
    }
}
