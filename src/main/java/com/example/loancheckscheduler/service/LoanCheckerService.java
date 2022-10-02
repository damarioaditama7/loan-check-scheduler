package com.example.loancheckscheduler.service;

import com.example.loancheckscheduler.model.BankCustomer;
import com.example.loancheckscheduler.model.LoanPermissionStatus;
import com.example.loancheckscheduler.model.LoanRequestData;
import com.example.loancheckscheduler.repository.BankCustomerRepository;
import com.example.loancheckscheduler.repository.LoanRequestDataRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LoanCheckerService {
    private final Gson gson = new GsonBuilder().disableHtmlEscaping().serializeNulls().create();

    private static final Integer LOAN_DEADLINE_THIRTY_DAY = 30;
    private static final Double ZERO_VALUE_AMOUNT = 0.00;

    @Autowired
    private LoanRequestDataRepository loanRequestDataRepository;

    @Autowired
    private BankCustomerRepository bankCustomerRepository;

    public String addLoanRequest(JsonObject data) {
        try {
            String stringData = gson.toJson(data);
            System.out.println("String: " + stringData);

            JsonObject loanRequestObject = gson.fromJson(stringData, JsonObject.class);
            String stringCustomerId = loanRequestObject.get("customerId") != null ? loanRequestObject.get("customerId").getAsString() : "";
            String stringLoanAmount = loanRequestObject.get("loanAmount") != null ? loanRequestObject.get("loanAmount").getAsString() : "";


            Long customerId = Long.parseLong(stringCustomerId);
            System.out.println("Customer Id: " + customerId);

            Double loanAmount = Double.parseDouble(stringLoanAmount);
            System.out.println("Loan Amount: " + loanAmount);

            BankCustomer bankCustomer = bankCustomerRepository.getBankCustomerById(customerId);
            System.out.println("Bank Customer: " + bankCustomer);

            LoanRequestData loanRequestData = new LoanRequestData();
            loanRequestData.setCustomerId(bankCustomer);
            loanRequestData.setLoanDate(new Date());
            loanRequestData.setLoanDeadline(LOAN_DEADLINE_THIRTY_DAY);
            loanRequestData.setLoanAmount(loanAmount);
            loanRequestData.setPenaltyAmount(ZERO_VALUE_AMOUNT);
            loanRequestData.setLoanPermissionStatus(LoanPermissionStatus.APPROVED);
            loanRequestData.setPaidAmount(ZERO_VALUE_AMOUNT);
            loanRequestData.setTotalAmount(loanAmount);
            loanRequestDataRepository.save(loanRequestData);

            LoanRequestData getNewLoanRequest = loanRequestDataRepository.getLoanRequestById(loanRequestData.getId());
            return "Add new loan request success with id: "+getNewLoanRequest.getId()+" and Customer: "+getNewLoanRequest.getCustomerId().getName();
        }catch (Exception e){
            return "Error: "+e.getMessage();
        }
    }

    public String forceExpireLoanDueDateById(JsonObject id){
        try{
            String stringData = gson.toJson(id);
            System.out.println("String: " + stringData);

            JsonObject loanRequestObject = gson.fromJson(stringData, JsonObject.class);
            String stringId = loanRequestObject.get("id") != null ? loanRequestObject.get("id").getAsString() : "";

            Long longId = Long.parseLong(stringId);

            LoanRequestData loanRequestData = loanRequestDataRepository.getLoanRequestById(longId);
            System.out.println("Data: "+loanRequestData);
            loanRequestData.setLoanDeadline(0);
            loanRequestDataRepository.save(loanRequestData);
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }

        return "Force expiry success";
    }


    public void checkLoanPenalty() {
        List<LoanRequestData> loanRequests = loanRequestDataRepository.getApprovedLoanRequest(LoanPermissionStatus.APPROVED.toString());
        loanRequests.
                stream()
                .filter(loanRequestsData -> loanRequestsData.getLoanDeadline() == 0)
                .forEach(this::setPenalty);

    }

    private void setPenalty(LoanRequestData loanRequestsData) {
        final Double PENALTY_PERCENTAGE = 0.01;
        final Integer DUE_DATE_AFTER_PENALTY = 30;

        Double penaltyAmount = loanRequestsData.getLoanAmount() * PENALTY_PERCENTAGE;
        Double totalAmount = loanRequestsData.getTotalAmount() + loanRequestsData.getPenaltyAmount();

        loanRequestsData.setPenaltyAmount(penaltyAmount);
        loanRequestsData.setTotalAmount(totalAmount);
        loanRequestsData.setLoanDeadline(DUE_DATE_AFTER_PENALTY);
        loanRequestDataRepository.save(loanRequestsData);
    }

    public void checkingLoanDueDateScheduler() {
        this.checkLoanPenalty();
    }
}
