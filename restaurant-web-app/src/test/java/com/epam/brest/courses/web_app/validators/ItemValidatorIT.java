package com.epam.brest.courses.web_app.validators;

import com.epam.brest.courses.model.Item;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.thymeleaf.util.StringUtils;

import java.math.BigDecimal;

import static com.epam.brest.courses.constants.ItemConstants.ITEM_NAME_SIZE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ItemValidatorIT {
    private Item item;

    private ItemValidator itemValidator = new ItemValidator();
    private BindingResult result;

    @BeforeEach
    void setup(){
        item = Mockito.mock(Item.class);
        result = new BeanPropertyBindingResult(item, "item");
    }


    @Test
    void shouldRejectEmptyItemName() {
        // given
        Mockito.when(item.getItemName()).thenReturn("");
        Mockito.when(item.getItemPrice()).thenReturn(new BigDecimal(1));

        // when
        itemValidator.validate(item, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectNullItemName(){
        // given
        Mockito.when(item.getItemName()).thenReturn(null);
        Mockito.when(item.getItemPrice()).thenReturn(new BigDecimal(1));

        // when
        itemValidator.validate(item, result);

        // then
        assertTrue(result.hasErrors());

    }

    @Test
    void shouldRejectLargeItemName() {

        // given
        String filled = StringUtils.repeat("*", ITEM_NAME_SIZE + 1);
        Mockito.when(item.getItemName()).thenReturn(filled);
        Mockito.when(item.getItemPrice()).thenReturn(new BigDecimal(1));

        // when
        itemValidator.validate(item, result);

        // then
        assertTrue(result.hasErrors());
    }

    @Test
    void shouldValidateItemName() {

        // given
        String filled = StringUtils.repeat("*", ITEM_NAME_SIZE);
        Mockito.when(item.getItemName()).thenReturn(filled);
        Mockito.when(item.getItemPrice()).thenReturn(new BigDecimal(1));

        // when
        itemValidator.validate(item, result);

        // then
        assertFalse(result.hasErrors());
    }

    @Test
    void shouldRejectItemPriceLessThenZero() {
        // given
        Mockito.when(item.getItemName()).thenReturn(RandomStringUtils.randomAlphabetic(ITEM_NAME_SIZE));
        Mockito.when(item.getItemPrice()).thenReturn(new BigDecimal(-1));

        // when
        itemValidator.validate(item, result);

        // then
        assertTrue(result.hasErrors());
    }
}
