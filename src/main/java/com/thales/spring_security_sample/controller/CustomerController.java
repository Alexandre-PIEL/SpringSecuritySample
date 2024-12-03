package com.thales.spring_security_sample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @GetMapping("/customer")
    public String getCustomerDetails() {
        return "Details of customer";
    }

    @GetMapping("/customers")
    public String getCustomersList() {
        return "List of customers";
    }

}
