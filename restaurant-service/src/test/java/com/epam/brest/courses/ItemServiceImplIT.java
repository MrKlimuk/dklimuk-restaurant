package com.epam.brest.courses;

import com.epam.brest.courses.model.Item;
import com.epam.brest.courses.service.ItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration({"classpath*:test-db.xml", "classpath*:test-service.xml", "classpath:dao.xml"})
public class ItemServiceImplIT {

    private final ItemService itemService;

    @Autowired
    public ItemServiceImplIT(ItemService itemService) {
        this.itemService = itemService;
    }

    @Test
    public void shouldFindAll(){
        List<Item> items = itemService.findAllItem();
        assertNotNull(items);
        assertTrue(items.size() > 0);
    }
}


