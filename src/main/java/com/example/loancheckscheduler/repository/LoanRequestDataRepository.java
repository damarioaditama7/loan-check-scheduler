package com.example.loancheckscheduler.repository;

import com.example.loancheckscheduler.model.LoanRequestData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoanRequestDataRepository extends JpaRepository<LoanRequestData,Long> {
    @Query(value = "SELECT * FROM loan_request_data WHERE loan_permission_status like CONCAT('%', :status, '%')", nativeQuery = true)
    List<LoanRequestData> getApprovedLoanRequest(String status);

}
