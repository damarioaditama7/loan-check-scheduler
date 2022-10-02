package com.example.loancheckscheduler.repository;

import com.example.loancheckscheduler.model.BankCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BankCustomerRepository extends JpaRepository<BankCustomer,Long> {
    @Query(value = "SELECT * FROM bank_customer WHERE id = :id", nativeQuery = true)
    BankCustomer getBankCustomerById(Long id);
}
