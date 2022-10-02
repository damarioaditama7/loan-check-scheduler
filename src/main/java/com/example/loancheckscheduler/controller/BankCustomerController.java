package com.example.loancheckscheduler.controller;

import com.example.loancheckscheduler.service.BankCustomerService;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/customer")
public class BankCustomerController {

    @Autowired
    private BankCustomerService bankCustomerService;

    @PostMapping (value = "/add_customer")
    public String addCustomer(@RequestBody String requestBody) {
        System.out.println(requestBody);
        JsonObject jsonObject = new GsonBuilder().create().fromJson(requestBody, JsonObject.class);
        return bankCustomerService.addBankCustomer(jsonObject);
    }
}
