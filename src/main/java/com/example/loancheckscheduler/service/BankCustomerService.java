package com.example.loancheckscheduler.service;

import com.example.loancheckscheduler.model.BankCustomer;
import com.example.loancheckscheduler.repository.BankCustomerRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankCustomerService {
    private final Gson gson = new GsonBuilder().disableHtmlEscaping().serializeNulls().create();

    @Autowired
    private BankCustomerRepository bankCustomerRepository;

    public String addBankCustomer(JsonObject data) {
        try {
            String stringData = gson.toJson(data);
            System.out.println("String: " + stringData);

            JsonObject loanRequestObject = gson.fromJson(stringData, JsonObject.class);
            String name = loanRequestObject.get("name") != null ? loanRequestObject.get("name").getAsString() : "";
            String address = loanRequestObject.get("address") != null ? loanRequestObject.get("address").getAsString() : "";
            String stringIncome = loanRequestObject.get("income") != null ? loanRequestObject.get("income").getAsString() : "";
            String phoneNumber = loanRequestObject.get("phoneNumber") != null ? loanRequestObject.get("phoneNumber").getAsString() : "";

            Double income = Double.parseDouble(stringIncome);
            System.out.println("Customer income: " + income);

            BankCustomer bankCustomer = new BankCustomer();
            bankCustomer.setName(name);
            bankCustomer.setAddress(address);
            bankCustomer.setIncome(income);
            bankCustomer.setPhoneNumber(phoneNumber);
            bankCustomerRepository.save(bankCustomer);

            return "Add new Bank Customer success with name: "+bankCustomer.getName();
        }catch (Exception e){
            return "Error: "+e;
        }
    }
}
