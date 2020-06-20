package com.epam.brest.courses;

import com.epam.brest.courses.model.Item;
import com.epam.brest.courses.service.ItemService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.constants.ItemConstants.ITEM_NAME_SIZE;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("dev")
@ContextConfiguration({"classpath*:test-db.xml", "classpath*:test-service.xml", "classpath:dao.xml"})
public class ItemServiceImplIT {

    private final ItemService itemService;
    private final BigDecimal ITEM_PRICE = new BigDecimal(100);
    private final BigDecimal ITEM_PRICE_FOR_UPDATE = new BigDecimal(10);

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

    @Test
    public void shouldFindItemById(){
        Item item = new Item()
                .setItemName(RandomStringUtils
                        .randomAlphabetic(ITEM_NAME_SIZE))
                .setItemPrice(ITEM_PRICE);

        Integer id = itemService.createItem(item);
        Optional<Item> itemOptional = itemService.findItemById(id);

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

        Integer id = itemService.createItem(item);
        Assert.assertNotNull(id);
    }

    @Test
    public void shouldUpdateItem(){
        Item item = new Item()
                .setItemName(RandomStringUtils.randomAlphabetic(ITEM_NAME_SIZE))
                .setItemPrice(ITEM_PRICE);
        Integer id = itemService.createItem(item);
        Assert.assertNotNull(id);

        Optional<Item> itemOptional = itemService.findItemById(id);
        Assertions.assertTrue(itemOptional.isPresent());

        itemOptional.get().setItemName(RandomStringUtils.randomAlphabetic(ITEM_NAME_SIZE))
                .setItemPrice(ITEM_PRICE_FOR_UPDATE);
        int result = itemService.updateItem(itemOptional.get());
        Assert.assertTrue(1 == result);

        Optional<Item> optionalUpdateItem = itemService.findItemById(id);
        Assertions.assertTrue(optionalUpdateItem.isPresent());

        assertEquals(optionalUpdateItem.get().getItemId(), id);
        assertEquals(optionalUpdateItem.get().getItemName(), itemOptional.get().getItemName());
        assertEquals(optionalUpdateItem.get().getItemPrice(), itemOptional.get().getItemPrice());

    }

    @Test
    public void shouldDeleteItem(){
        Item item = new Item()
                .setItemName(RandomStringUtils.randomAlphabetic(ITEM_NAME_SIZE))
                .setItemPrice(ITEM_PRICE);
        Integer id = itemService.createItem(item);

        List<Item> items = itemService.findAllItem();
        Assert.assertNotNull(items);

        int result = itemService.deleteItem(id);
        Assert.assertTrue(1 == result);

        List<Item> currentItems = itemService.findAllItem();
        Assert.assertNotNull(currentItems);

        Assert.assertTrue(items.size() - 1 == currentItems.size());
    }
}


