package org.example.bo.custom;

import org.example.bo.SuperBo;
import org.example.dto.PaymentDTO;
import org.example.entity.Payment;
import org.example.entity.Register;

import java.util.List;

public interface PaymentBO extends SuperBo{
    String getCurrentId();
    int getPaidAmountByRegisterId(Register selectedRegisterId);
    int getFullFeeAmountByRegisterId(Register selectedRegisterId);
    int getRemainingFeeAmountByRegisterId(Register selectedRegisterId);
    boolean save (PaymentDTO paymentDTO);
    Payment search(String id);
    List<PaymentDTO>getAll();
}
