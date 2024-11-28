package org.example.controller.admin;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.bo.BOFactory;
import org.example.bo.custom.PaymentBO;
import org.example.dto.PaymentDTO;
import org.example.entity.Payment;
import org.example.entity.Student;
import org.example.entity.tm.PaymentTm;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AdminPaymentFormController {
    public TextField txtIds;
    public JFXButton btnSearch;
    public TableView<PaymentTm> tblPayment;
    public TableColumn<?,?> clmPaymentId;
    public TableColumn<?,?> clmRegisterId;
    public TableColumn<?,?> clmFullCourseFee;
    public TableColumn<?,?> clmAdvancedAmount;
    public TableColumn<?,?> clmRemaining;
    public TableColumn<?,?> clmNewPayment;
    public TableColumn<?,?> clmBalance;
    public Label lblDate;

    PaymentBO paymentBO = (PaymentBO) BOFactory.getBOFactory().getBOType(BOFactory.BOType.Payment);

    public void initialize() {
        setCellValueFactory();
        loadAllPayments();
        setDateToLabel();
    }

    private void loadAllPayments() {
        ObservableList<PaymentTm> paymentsTm = FXCollections.observableArrayList();
        List<PaymentDTO> paymentDTOS = paymentBO.getAll();
        for (PaymentDTO paymentDTO : paymentDTOS) {
            PaymentTm paymentTm = new PaymentTm(
                    paymentDTO.getPaymentId(),
                    paymentDTO.getAmount(),
                    paymentDTO.getPaidAmount(),
                    paymentDTO.getFullPayment(),
                    paymentDTO.getPay(),
                    paymentDTO.getBalance(),
                    paymentDTO.getRegister()
            );
            paymentsTm.add(paymentTm);
        }
        tblPayment.setItems(paymentsTm);
    }

    private void setCellValueFactory() {
        clmPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        clmRegisterId.setCellValueFactory(new PropertyValueFactory<>("registerId"));
        clmFullCourseFee.setCellValueFactory(new PropertyValueFactory<>("fullCourseFee"));
        clmAdvancedAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        clmRemaining.setCellValueFactory(new PropertyValueFactory<>("paidAmount"));
        clmNewPayment.setCellValueFactory(new PropertyValueFactory<>("pay"));
        clmBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
    }

    public void setDateToLabel() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        lblDate.setText(currentDate.format(formatter));
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String paymentId = txtIds.getText();
        Payment payment = paymentBO.search(paymentId);

        if (payment != null) {
            System.out.println("Payment Found!");

            String paymentDetails = "Payment ID: " + payment.getPaymentId() + "\n" +
                    "Advanced Amount: " + payment.getAmount() + "\n" +
                    "Full Fee: " + payment.getFullPayment() + "\n" +
                    "Paid Amount: " + payment.getPaidAmount() + "\n" +
                    "New Payment: " + payment.getPay() + "\n" +
                    "Balance Amount: " + payment.getBalance();

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Payment Found!", ButtonType.OK);
            TextArea detailsArea = new TextArea(paymentDetails);
            detailsArea.setEditable(false);
            detailsArea.setWrapText(true);
            detailsArea.setPrefSize(300, 150);

            alert.getDialogPane().setContent(detailsArea);
            alert.showAndWait();
            cleanFields();
        } else {
            new Alert(Alert.AlertType.ERROR, "Payment Not Found!", ButtonType.OK).show();
        }
    }

    private void cleanFields() {
        txtIds.setText("");
    }
}
