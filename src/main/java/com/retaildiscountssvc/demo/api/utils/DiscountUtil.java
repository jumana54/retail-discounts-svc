package com.retaildiscountssvc.demo.api.utils;

import com.retaildiscountssvc.demo.api.model.Item;
import com.retaildiscountssvc.demo.api.model.ItemType;

import java.util.List;

public class DiscountUtil {

    private DiscountUtil() {
    }

    public static double getDiscountableAmount(final List<Item> items) {
        return items.stream()
                .filter(item -> !ItemType.GROCERY.equals(item.getType()))
                .mapToDouble(Item::getPrice)
                .sum();
    }

    public static double getNonDiscountableAmount(final List<Item> items) {
        return items.stream()
                .filter(item -> ItemType.GROCERY.equals(item.getType()))
                .mapToDouble(Item::getPrice)
                .sum();
    }
}
