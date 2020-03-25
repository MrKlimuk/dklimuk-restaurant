package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Item;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.constants.ItemConstants.ITEM_NAME_SIZE;
import static org.junit.Assert.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath*:dao.xml"})
public class ItemDaoJdbcIT {

    private final ItemDao itemDao;
    private final BigDecimal ITEM_PRICE = new BigDecimal(100);
    private final BigDecimal ITEM_PRICE_FOR_UPDATE = new BigDecimal(10);

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

    @Test
    public void shouldFindItemById(){
        Item item = new Item()
                .setItemName(RandomStringUtils
                .randomAlphabetic(ITEM_NAME_SIZE))
                .setItemPrice(ITEM_PRICE);

        Integer id = itemDao.create(item);
        Optional<Item> itemOptional = itemDao.findById(id);

        Assertions.assertTrue(itemOptional.isPresent());
        assertEquals(itemOptional.get().getItemId(), id);
        assertEquals(itemOptional.get().getItemName(), item.getItemName());
        assertEquals(itemOptional.get().getItemPrice(), item.getItemPrice());
    }

    @Test
    public void shouldCreateItem(){
      Item item = new Item()
              .setItemName(RandomStringUtils.randomAlphabetic(ITEM_NAME_SIZE))
              .setItemPrice(ITEM_PRICE);

      Integer id = itemDao.create(item);
      assertNotNull(id);
    }

}

