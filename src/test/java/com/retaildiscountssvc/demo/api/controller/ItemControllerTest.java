package com.retaildiscountssvc.demo.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retaildiscountssvc.demo.api.model.Item;
import com.retaildiscountssvc.demo.api.model.ItemType;
import com.retaildiscountssvc.demo.api.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = ItemController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ItemService itemService;
    @Autowired
    private ObjectMapper objectMapper;
    private Item item1;
    private Item item2;
    private List<Item> items;

    @BeforeEach
    public void init() {
        item1 = Item.builder().id(1).price(1.5).type(ItemType.GROCERY).build();
        item2 = Item.builder().id(2).price(10).type(ItemType.ROOM).build();
        items = asList(item1, item2);
    }

    @Test
    public void ItemController_AddItems_ReturnOk() throws Exception {
        given(itemService.saveItems(any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/item")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(items)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(item1.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price", is(item1.getPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type", is(item1.getType().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", is(item2.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].price", is(item2.getPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].type", is(item2.getType().toString())));
    }

    @Test
    public void ItemController_GetItems_ReturnResponseDto() throws Exception {
        when(itemService.findAllItems()).thenReturn(items);

        ResultActions response = mockMvc.perform(get("/item"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(item1.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price", is(item1.getPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type", is(item1.getType().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", is(item2.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].price", is(item2.getPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].type", is(item2.getType().toString())));
    }

    @Test
    public void ItemController_GetItem_ReturnResponseDto() throws Exception {
        when(itemService.findItemById(anyInt())).thenReturn(item1);

        ResultActions response = mockMvc.perform(get("/item/1"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(item1.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", is(item1.getPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", is(item1.getType().toString())));
    }

    @Test
    public void ItemController_GetItem_ReturnNotFound() throws Exception {
        when(itemService.findItemById(anyInt())).thenReturn(null);

        ResultActions response = mockMvc.perform(get("/item/1"));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
