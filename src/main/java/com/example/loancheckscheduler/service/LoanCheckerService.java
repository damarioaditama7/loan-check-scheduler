package com.example.loancheckscheduler.service;

import com.example.loancheckscheduler.model.LoanPermissionStatus;
import com.example.loancheckscheduler.model.LoanRequestData;
import com.example.loancheckscheduler.repository.LoanRequestDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanCheckerService {

    private static final Logger logger = LoggerFactory.getLogger(LoanCheckerService.class);

    @Autowired
    private LoanRequestDataRepository loanRequestDataRepository;


    public void checkLoanPenalty() {
        List<LoanRequestData> loanRequests = loanRequestDataRepository.getApprovedLoanRequest(LoanPermissionStatus.APPROVED.toString());
        loanRequests.
                stream()
                .filter(loanRequestsData -> loanRequestsData.getLoanDeadline() == 0)
                .forEach(this::setPenalty);

    }

    private void setPenalty(LoanRequestData loanRequestsData){
        logger.debug("Loan data with id: "+loanRequestsData.getId()+" get a penalty.");
        final Double PENALTY_PERCENTAGE = 0.01;
        final Integer DUE_DATE_AFTER_PENALTY = 30;

        Double penaltyAmount = loanRequestsData.getLoanAmount() * PENALTY_PERCENTAGE;
        Double totalAmount = loanRequestsData.getTotalAmount() + loanRequestsData.getPenaltyAmount();

        loanRequestsData.setPenaltyAmount(penaltyAmount);
        loanRequestsData.setTotalAmount(totalAmount);
        loanRequestsData.setLoanDeadline(DUE_DATE_AFTER_PENALTY);
        loanRequestDataRepository.save(loanRequestsData);
}

    public void checkingLoanDueDateScheduler(){
        this.checkLoanPenalty();
    }
}
