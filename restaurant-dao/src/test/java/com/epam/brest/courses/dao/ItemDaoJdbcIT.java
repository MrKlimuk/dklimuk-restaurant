package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath*:dao.xml"})
public class ItemDaoJdbcIT {

    private final ItemDao itemDao;

    @Autowired
    public ItemDaoJdbcIT(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Test
    public void shouldFindAllItem(){
        List<Item>items = itemDao.findAll();
        assertNotNull(items);
        assertTrue(items.size() > 0);
    }
}

