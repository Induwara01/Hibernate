package org.example.dao.custom;

import org.example.dao.CrudDAO;
import org.example.entity.Payment;
import org.example.entity.Register;

import java.util.List;

public interface PaymentDAO extends CrudDAO {
    String getCurrentId();
    int getPaidAmountByRegisterId(Register registerId);
    int getFullFeeByRegisterId(Register selectedRegisterId);
    int getRemainingAmountByRegisterId(Register selectedRegisterId);
}
