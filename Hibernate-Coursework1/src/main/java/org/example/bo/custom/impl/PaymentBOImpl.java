package org.example.bo.custom.impl;

import org.example.bo.custom.PaymentBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.PaymentDAO;
import org.example.dto.PaymentDTO;
import org.example.entity.Payment;
import org.example.entity.Register;

import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDaoFactory().getDAOType(DAOFactory.DAOType.Payment);

    @Override
    public String getCurrentId() {
        return paymentDAO.getCurrentId();
    }

    @Override
    public int getPaidAmountByRegisterId(Register selectedRegisterId) {
        return paymentDAO.getPaidAmountByRegisterId(selectedRegisterId);
    }

    @Override
    public int getFullFeeAmountByRegisterId(Register selectedRegisterId) {
        return paymentDAO.getFullFeeByRegisterId(selectedRegisterId);
    }

    @Override
    public int getRemainingFeeAmountByRegisterId(Register selectedRegisterId) {
        return paymentDAO.getRemainingAmountByRegisterId(selectedRegisterId);
    }

    @Override
    public boolean save(PaymentDTO paymentDTO) {
        return paymentDAO.save(new Payment(paymentDTO.getPaymentId(),
                paymentDTO.getAmount(),
                paymentDTO.getPaidAmount(),
                paymentDTO.getFullPayment(),
                paymentDTO.getPay(),
                paymentDTO.getBalance(),
                paymentDTO.getRegister()
        ));
    }

    @Override
    public Payment search(String id) {
        return null;
    }


    @Override
    public List<PaymentDTO> getAll() {
        List<PaymentDTO>paymentDTOS = new ArrayList<>();
        List<Payment> payments = paymentDAO.getAll();
        for (Payment payment : payments) {
            paymentDTOS.add(new PaymentDTO(
                    payment.getPaymentId(),
                    payment.getAmount(),
                    payment.getPaidAmount(),
                    payment.getFullPayment(),
                    payment.getPay(),
                    payment.getBalance(),
                    payment.getRegister()
            ));
        }
        return paymentDTOS;
    }
}
