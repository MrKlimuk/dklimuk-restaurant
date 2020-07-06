package com.epam.brest.courses.web_app;

import com.epam.brest.courses.model.Item;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static com.epam.brest.courses.constants.ItemConstants.ITEM_NAME_SIZE;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ActiveProfiles("dev")
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
@Transactional
public class WebItemControllerIT {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void shouldOpenPageHello() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.get("/")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("hello")
                );
    }

    @Test
    public void shouldReturnItemPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/items")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("items"))
                .andExpect(model().attribute("items", hasItem(
                        allOf(
                                hasProperty("itemId", is(1)),
                                hasProperty("itemName", is("Vino")),
                                hasProperty("itemPrice", is(new BigDecimal(100)))
                        )
                )))
                .andExpect(model().attribute("items", hasItem(
                        allOf(
                                hasProperty("itemId", is(2)),
                                hasProperty("itemName", is("Coffee")),
                                hasProperty("itemPrice", is(new BigDecimal(30)))
                        )
                )))
        ;
    }

    @Test
    public void shouldOpenAddItemPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/item/add")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("itemAdd")
                );
    }

    @Test
    public void shouldAddNewItem() throws Exception {

        Item item = new Item()
                .setItemName(RandomStringUtils.randomAlphabetic(ITEM_NAME_SIZE))
                .setItemPrice(new BigDecimal(10));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/item/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("itemName", item.getItemName())
                        .param("itemPrice", String.valueOf(item.getItemPrice()))
                        .sessionAttr("item", item)
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/items"))
                .andExpect(redirectedUrl("/items"));
    }


    @Test
    public void shouldOpenGenerateItemsPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/items/generate")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("itemGenerate")
                );
    }

    @Test
    public void shouldGenerateItems() throws Exception {


        mockMvc.perform(
                MockMvcRequestBuilders.post("/items/generate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("number", "10")
                        .param("language", "EN")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/items"))
                .andExpect(redirectedUrl("/items"));
    }

    @Test
    public void shouldOpenEditItemPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/items/edit/1")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("itemEdit"))
                .andExpect(model().attribute("item", hasProperty("itemId", is(1))))
                .andExpect(model().attribute("item", hasProperty("itemName", is("Vino"))))
                .andExpect(model().attribute("item", hasProperty("itemPrice", is(new BigDecimal(100))))
                );
    }

    @Test
    public void shouldUpdateItem() throws Exception{
        Item item = new Item()
                .setItemId(1)
                .setItemName(RandomStringUtils.randomAlphabetic(ITEM_NAME_SIZE))
                .setItemPrice(new BigDecimal(10));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/items/edit/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("itemId", String.valueOf(item.getItemId()))
                        .param("itemName", item.getItemName())
                        .param("itemPrice", String.valueOf(item.getItemPrice()))
                        .sessionAttr("item", item)
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/items"))
                .andExpect(redirectedUrl("/items"));

    }


    @Test
    public void shouldDeleteItemById() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/items/delete/1")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/items"))
                .andExpect(redirectedUrl("/items"));
    }


}
