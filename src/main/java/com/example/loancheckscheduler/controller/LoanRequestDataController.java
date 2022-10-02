package com.example.loancheckscheduler.controller;

import com.example.loancheckscheduler.service.LoanCheckerService;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/loan_request")
public class LoanRequestDataController {
    @Autowired
    private LoanCheckerService loanCheckerService;

    public LoanRequestDataController(LoanCheckerService loanCheckerService) {
        this.loanCheckerService = loanCheckerService;
    }

    @PostMapping(value = "add_data")
    public String addLoanRequest(@RequestBody String requestBody){
        System.out.println("Data: "+requestBody);
        JsonObject jsonObject = new GsonBuilder().create().fromJson(requestBody, JsonObject.class);
        return loanCheckerService.addLoanRequest(jsonObject);
    }

    @PostMapping(value = "force_expire")
    public String forceExpireLoanDueDate(@RequestBody String loanRequestId){
        System.out.println("Id: "+loanRequestId);
        JsonObject jsonObject = new GsonBuilder().create().fromJson(loanRequestId, JsonObject.class);
        return loanCheckerService.forceExpireLoanDueDateById(jsonObject);
    }
}
