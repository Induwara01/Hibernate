package org.example.entity.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.Register;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentTm {
    private String paymentId;
    private int amount;
    private int paidAmount;
    private int fullPayment;
    private int pay;
    private int balance;
    private Register register;

    public String getRegisterId() {
        return register != null ? register.getRegisterId() : null;
    }

    public int getFullCourseFee() {
        return register != null ? register.getCourse().getFee() : 0;
    }
}