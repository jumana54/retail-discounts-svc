package com.retaildiscountssvc.demo.api.service;

import com.retaildiscountssvc.demo.api.model.Item;
import com.retaildiscountssvc.demo.api.model.ItemType;
import com.retaildiscountssvc.demo.api.model.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DiscountsServiceTest {

    @InjectMocks
    private DiscountsService discountsService;
    private List<Item> items;

    @BeforeEach
    public void init() {
        final Item item1 = Item.builder().id(1).price(7).type(ItemType.GROCERY).build();
        final Item item2 = Item.builder().id(2).price(90).type(ItemType.ROOM).build();
        final Item item3 = Item.builder().id(3).price(57.5).type(ItemType.YARD).build();
        items = asList(item1, item2, item3);
    }

    @Test
    public void DiscountsService_GetPercentageDiscountedAmount_EmployeeDiscount() {
        assertEquals(103.25, discountsService.getPercentageDiscountedAmount(UserType.EMPLOYEE, null, items));
    }

    @Test
    public void DiscountsService_GetPercentageDiscountedAmount_AffiliateDiscount() {
        assertEquals(132.75, discountsService.getPercentageDiscountedAmount(UserType.AFFILIATE, null, items));
    }

    @Test
    public void DiscountsService_GetPercentageDiscountedAmount_CustomerDiscountForMoreThanTwoYears() {
        assertEquals(140.125, discountsService.getPercentageDiscountedAmount(UserType.CUSTOMER, LocalDate.now().minusYears(2), items));
    }

    @Test
    public void DiscountsService_GetPercentageDiscountedAmount_CustomerNoDiscountForLessThanTwoYears() {
        assertEquals(147.5, discountsService.getPercentageDiscountedAmount(UserType.CUSTOMER, LocalDate.now().minusYears(1), items));
    }

    @Test()
    public void DiscountsService_GetBaseDiscountedAmount_RoundedHundred() {
        assertEquals(190, discountsService.getBaseDiscountedAmount(200));
    }

    @Test()
    public void DiscountsService_GetBaseDiscountedAmount_NonRoundedHundred() {
        assertEquals(185, discountsService.getBaseDiscountedAmount(190));
    }
}
