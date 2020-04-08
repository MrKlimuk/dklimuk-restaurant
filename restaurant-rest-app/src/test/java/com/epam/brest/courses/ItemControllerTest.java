package com.epam.brest.courses;


import com.epam.brest.courses.model.Item;
import com.epam.brest.courses.service.ItemService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {

    @InjectMocks
    private ItemController controller;

    @Mock
    private ItemService itemService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

    @AfterEach
    public void end(){
        Mockito.verifyNoMoreInteractions(itemService);
    }

    @Test
    public void version() throws Exception {

        Mockito.when(itemService.findAllItem()).thenReturn(Arrays.asList(create(0), create(1)));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/items")
        ).andDo(MockMvcResultHandlers.print())
                   .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].itemId", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].itemName", Matchers.is("NameItem0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].itemPrice", Matchers.is(100)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].itemId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].itemName", Matchers.is("NameItem1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].itemPrice", Matchers.is(101)))
        ;

    }


    private Item create(int index){
        Item item = new Item();
        item.setItemId(index);
        item.setItemName("NameItem" + index);
        item.setItemPrice(new BigDecimal(100 + index));
        return item;
    }
}
