package com.example.loancheckscheduler.scheduler;

import com.example.loancheckscheduler.service.LoanCheckerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class LoanCheckerScheduler {

    private final LoanCheckerService loanCheckerService;

    public LoanCheckerScheduler(LoanCheckerService loanCheckerService) {
        this.loanCheckerService = loanCheckerService;
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    private void checkLoanDueDate(){
        System.out.println("Start the scheduler at "+new Date());
        loanCheckerService.checkingLoanDueDateScheduler();
    }
}
