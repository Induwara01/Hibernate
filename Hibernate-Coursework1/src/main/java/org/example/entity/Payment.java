package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Payment {
    @Id
    @Column(name = "payment_id") // Renamed column for clarity
    private String paymentId;

    @Column(name = "advanced_amount") // Renaming column for advanced payment
    private int amount;

    @Column(name = "full_fee") // Renaming column for total paid amount
    private int paidAmount;

    @Column(name = "past_reamaining_amount") // Renaming column for full fee
    private int fullPayment;

    @Column(name = "new_payment") // Renaming column for the new payment amount
    private int pay;

    @Column(name = "balance_amount") // Renaming column for the balance
    private int balance;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "register_id") // Rename foreign key column in the table
    private Register register;
}