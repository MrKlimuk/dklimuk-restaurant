package com.epam.brest.courses.web_app;

import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.model.Position;
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
import java.time.LocalDate;

import static com.epam.brest.courses.constants.OrderConstants.ORDER_NAME_SIZE;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ActiveProfiles("dev")
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
@Transactional
public class OrderControllerIT {

    private final LocalDate DATE = LocalDate.of(2020, 4, 18);
    private final LocalDate START_DATE = LocalDate.of(2020, 4, 10);
    private final LocalDate END_DATE = LocalDate.of(2020, 4, 18);

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void shouldReturnOrderPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/orders")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("orders"))
                .andExpect(model().attribute("orders", hasItem(
                        allOf(
                                hasProperty("orderId", is(1)),
                                hasProperty("orderName", is("Table #1")),
                                hasProperty("orderPrice", is(new BigDecimal(40)))
                        )
                )))
                .andExpect(model().attribute("orders", hasItem(
                        allOf(
                                hasProperty("orderId", is(2)),
                                hasProperty("orderName", is("Reserved")),
                                hasProperty("orderPrice", is(new BigDecimal(210)))
                        )
                )))
        ;
    }

    @Test
    public void shouldOpenAddItemPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/order/add")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("orderAdd")
                );
    }

    @Test
    public void shouldFindOrderByDate() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/orders")
                        .param("startDateString", String.valueOf(START_DATE))
                        .param("endDateString", String.valueOf(END_DATE))
                        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF8"))
                .andExpect(MockMvcResultMatchers.view().name("orders"));
    }

    @Test
    public void shouldAddNewOrder() throws Exception {

        Order order = new Order()
                .setOrderId(4)
                .setOrderName(RandomStringUtils.randomAlphabetic(ORDER_NAME_SIZE))
                .setOrderPrice(new BigDecimal(10))
                .setOrderDate(DATE);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/order/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("orderId", String.valueOf(order.getOrderId()))
                        .param("orderName", order.getOrderName())
                        .param("orderPrice", String.valueOf(order.getOrderPrice()))
                        .param("orderDateString", String.valueOf(order.getOrderDate()))
                        .sessionAttr("order", order).sessionAttr("order", order)
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/orders/edit/" + String.valueOf(order.getOrderId())))
                .andExpect(redirectedUrl("/orders/edit/" + String.valueOf(order.getOrderId()) + "?orderDateString=2020-04-18"));
    }

    @Test
    public void shouldAddItemToOrder() throws Exception {

        Integer id = 1;
        Integer orderId = 1;
        Position position = new Position()
                .setPositionId(id)
                .setPositionOrderId(orderId)
                .setPositionName("Tea")
                .setPositionPrice(new BigDecimal(10))
                .setPositionCount(1);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/orders/details/" + String.valueOf(orderId) +"/add/" + String.valueOf(id))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("positionId", String.valueOf(position.getPositionId()))
                        .param("positionOrderId", String.valueOf(position.getPositionOrderId()))
                        .param("positionName", position.getPositionName())
                        .param("positionPrice", String.valueOf(position.getPositionPrice()))
                        .param("positionCount", String.valueOf(position.getPositionCount()))
                        .sessionAttr("positions", position)
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/orders/edit/" + String.valueOf(orderId)))
                .andExpect(redirectedUrl("/orders/edit/" + String.valueOf(orderId)));
    }

    @Test
    public void shouldDeleteItemFromOrder() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/orders/details/1/delete/1")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/orders/edit/1"))
                .andExpect(redirectedUrl("/orders/edit/1"));
    }



    @Test
    public void shouldToToEditOrderPage() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/orders/edit/1")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("orderEdit"))
                .andExpect(model().attribute("order", hasProperty("orderId", is(1))))
                .andExpect(model().attribute("order", hasProperty("orderName", is("Table #1"))))
                .andExpect(model().attribute("order", hasProperty("orderPrice", is(new BigDecimal(40)))))
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
                .andExpect(model().attribute("positions", hasItem(
                        allOf(
                                hasProperty("positionId", is(1)),
                                hasProperty("positionOrderId", is(1)),
                                hasProperty("positionName", is("Tea")),
                                hasProperty("positionPrice", is(new BigDecimal(10))),
                                hasProperty("positionCount", is(1))
                        )
                )))

        ;
    }

    @Test
    public void shouldDeleteOrderById() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/orders/delete/1")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/orders"))
                .andExpect(redirectedUrl("/orders"));
    }

}
