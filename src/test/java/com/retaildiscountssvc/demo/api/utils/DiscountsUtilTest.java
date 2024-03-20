package com.retaildiscountssvc.demo.api.utils;

import com.retaildiscountssvc.demo.api.model.Item;
import com.retaildiscountssvc.demo.api.model.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountsUtilTest {

    private Item item1;
    private Item item2;
    private Item item3;

    @BeforeEach
    public void init() {
        item1 = Item.builder().id(1).price(7).type(ItemType.GROCERY).build();
        item2 = Item.builder().id(2).price(90).type(ItemType.ROOM).build();
        item3 = Item.builder().id(3).price(57.5).type(ItemType.YARD).build();
    }

    @Test
    public void DiscountsUtil_GetDiscountableAmount_ReturnDiscountableAmount() {
        assertEquals(147.5, DiscountUtil.getDiscountableAmount(asList(item1, item2, item3)));
    }

    @Test
    public void DiscountsUtil_GetNonDiscountableAmount_ReturnNonDiscountableAmount() {
        assertEquals(7, DiscountUtil.getNonDiscountableAmount(asList(item1, item2, item3)));
    }
}
