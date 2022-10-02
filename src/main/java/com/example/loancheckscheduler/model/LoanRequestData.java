package com.example.loancheckscheduler.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class LoanRequestData {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(targetEntity = BankCustomer.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_fk", nullable = false)
    private BankCustomer customerId;

    private Double loanAmount;

    private Double penaltyAmount;

    private Double paidAmount;

    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private LoanPermissionStatus loanPermissionStatus;

    private Date loanDate;

    private Integer loanDeadline;
}
