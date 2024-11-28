package org.example.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.entity.Register;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class PaymentDTO {
    @Id
    private String paymentId;
    private int amount;
    private int paidAmount;
    private int fullPayment;
    private int pay;
    private int balance;
    private Register register;
}
