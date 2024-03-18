package com.retaildiscountssvc.demo.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Period;

@RestController
public class discountsController {

    @GetMapping("/payable")
    public double getPayableAmount(@RequestParam(required = false) final UserType userType,
                                   @RequestParam final LocalDate joinDate,
                                   @RequestParam final double billedAmount) {
        return switch (userType) {
            case UserType.EMPLOYEE -> billedAmount - (billedAmount * 0.3);
            case UserType.AFFILIATE -> billedAmount - (billedAmount * 0.1);
            default ->
                    Period.between(joinDate, LocalDate.now()).getYears() < 2 ? billedAmount : billedAmount - (billedAmount * 0.05);
        };
    }
}
