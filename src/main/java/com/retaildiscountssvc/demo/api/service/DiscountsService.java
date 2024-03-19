package com.retaildiscountssvc.demo.api.service;

import com.retaildiscountssvc.demo.api.model.Item;
import com.retaildiscountssvc.demo.api.model.UserType;
import com.retaildiscountssvc.demo.api.utils.DiscountUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class DiscountsService {

    private static final int BASE_DISCOUNT = 5;

    public double getPercentageDiscountAmount(final UserType userType,
                                              final LocalDate joinDate,
                                              final List<Item> items) {
        final double discountableAmount = DiscountUtil.getDiscountableAmount(items);
        return switch (userType) {
            case UserType.EMPLOYEE -> discountableAmount - (discountableAmount * 0.3);
            case UserType.AFFILIATE -> discountableAmount - (discountableAmount * 0.1);
            default -> getYearsDiscount(discountableAmount, joinDate);
        };
    }

    public double getBaseDiscountedAmount(final double amount) {
        final int count = (int) amount / 100;
        final int discount = count * BASE_DISCOUNT;
        return amount - discount;
    }

    private double getYearsDiscount(final double amount, final LocalDate joinDate) {
        return Period.between(joinDate, LocalDate.now()).getYears() < 2 ? amount : amount - (amount * 0.05);
    }
}
