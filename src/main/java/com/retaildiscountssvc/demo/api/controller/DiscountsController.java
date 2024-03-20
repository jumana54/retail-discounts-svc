package com.retaildiscountssvc.demo.api.controller;

import com.retaildiscountssvc.demo.api.model.Item;
import com.retaildiscountssvc.demo.api.model.User;
import com.retaildiscountssvc.demo.api.service.DiscountsService;
import com.retaildiscountssvc.demo.api.service.ItemService;
import com.retaildiscountssvc.demo.api.service.UserService;
import com.retaildiscountssvc.demo.api.utils.DiscountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class DiscountsController {

    private final DiscountsService discountsService;
    private final ItemService itemService;
    private final UserService userService;

    DiscountsController(@Autowired DiscountsService discountsService,
                        @Autowired ItemService itemService,
                        @Autowired UserService userService) {
        this.discountsService = discountsService;
        this.itemService = itemService;
        this.userService = userService;
    }

    @GetMapping("/payable")
    public ResponseEntity<Double> getPayableAmount(@RequestParam final int userId,
                                                   @RequestParam final List<Integer> itemIds) throws IOException {
        final User user = userService.findUserById(userId);
        if (user == null) {
            throw new IOException("User not found");
        } else {
            final List<Item> items = itemService.findAllItemsById(itemIds);
            final double percentageDiscountedAmount = discountsService.getPercentageDiscountedAmount(user.getType(), user.getJoinDate(), items);
            final double nonDiscountableAmount = DiscountUtil.getNonDiscountableAmount(items);
            final double payableAmount = discountsService.getBaseDiscountedAmount(percentageDiscountedAmount + nonDiscountableAmount);
            return new ResponseEntity<>(payableAmount, HttpStatus.OK);
        }
    }
}
