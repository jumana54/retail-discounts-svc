package com.retaildiscountssvc.demo.api.controller;

import com.retaildiscountssvc.demo.api.model.User;
import com.retaildiscountssvc.demo.api.model.UserType;
import com.retaildiscountssvc.demo.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

@RestController
public class discountsController {

    private static final int BASE_DISCOUNT = 5;

    @Autowired
    private UserService userService;

    @GetMapping("/payable/v1")
    public double getPayableAmount(@RequestParam final UserType userType,
                                   @RequestParam final LocalDate joinDate,
                                   @RequestParam final double billedAmount) {
        return getPercentageDiscount(userType, joinDate, getBaseDiscount(billedAmount));
    }

    @GetMapping("/payable")
    public ResponseEntity<Double> getPayableAmount(@RequestParam final long userId,
                                                   @RequestParam final double billedAmount) throws IOException {
        final User user = userService.getUser(userId);
        if (user == null) {
            throw new IOException("User not found");
        } else {
            final double payableAmount = getPercentageDiscount(user.getType(), user.getJoinDate(), getBaseDiscount(billedAmount));
            return new ResponseEntity<>(payableAmount, HttpStatus.OK);
        }
    }

    private double getBaseDiscount(final double billedAmount) {
        final int count = (int) billedAmount / 100;
        final int discount = count * BASE_DISCOUNT;
        return billedAmount - discount;
    }

    private double getPercentageDiscount(final UserType userType,
                                         final LocalDate joinDate,
                                         final double billedAmount) {
        return switch (userType) {
            case UserType.EMPLOYEE -> billedAmount - (billedAmount * 0.3);
            case UserType.AFFILIATE -> billedAmount - (billedAmount * 0.1);
            default ->
                    Period.between(joinDate, LocalDate.now()).getYears() < 2 ? billedAmount : billedAmount - (billedAmount * 0.05);
        };
    }
}
