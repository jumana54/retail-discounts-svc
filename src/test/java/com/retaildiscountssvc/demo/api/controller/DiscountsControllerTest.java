package com.retaildiscountssvc.demo.api.controller;

import com.retaildiscountssvc.demo.api.model.Item;
import com.retaildiscountssvc.demo.api.model.ItemType;
import com.retaildiscountssvc.demo.api.model.User;
import com.retaildiscountssvc.demo.api.model.UserType;
import com.retaildiscountssvc.demo.api.service.DiscountsService;
import com.retaildiscountssvc.demo.api.service.ItemService;
import com.retaildiscountssvc.demo.api.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = DiscountsController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class DiscountsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DiscountsService discountsService;
    @MockBean
    private UserService userService;
    @MockBean
    private ItemService itemService;
    private Item item1;
    private Item item2;
    private Item item3;

    @BeforeEach
    public void init() {
        item1 = Item.builder().id(1).price(7).type(ItemType.GROCERY).build();
        item2 = Item.builder().id(2).price(90).type(ItemType.ROOM).build();
        item3 = Item.builder().id(3).price(57.5).type(ItemType.YARD).build();
    }

    @Test()
    public void DiscountsController_GetPayableAmount_UserNotFoundThrowException() {
        when(userService.findUserById(anyInt())).thenReturn(null);

        assertThrows(IOException.class, () -> mockMvc.perform(get("/payable")
                .param("userId", "1")
                .param("itemIds", "1")));
    }

    @Test
    public void DiscountsController_GetPayableAmount_ReturnPayableAmount() throws Exception {
        final User user = User.builder().id(123456789).name("test-user").type(UserType.EMPLOYEE).build();

        when(userService.findUserById(anyInt())).thenReturn(user);
        when(itemService.findAllItemsById(any())).thenReturn(asList(item1, item2, item3));
        when(discountsService.getPercentageDiscountedAmount(any(), any(), any())).thenReturn(103.25);
        when(discountsService.getBaseDiscountedAmount(anyDouble())).thenReturn(98.25);

        ResultActions response = mockMvc.perform(get("/payable")
                .param("userId", "1")
                .param("itemIds", "1", "2", "3"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("98.25"));
    }
}
