package com.example.loancheckscheduler.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class BankCustomer {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(
            mappedBy = "customerId",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private List<LoanRequestData> loanRequests;

    private String name;

    private String address;

    private String phoneNumber;

    private Double income;

    @Override
    public String toString() {
        return "BankCustomer{" +
                "id=" + id +
                ", loanRequests=" + loanRequests +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", income=" + income +
                '}';
    }
}
