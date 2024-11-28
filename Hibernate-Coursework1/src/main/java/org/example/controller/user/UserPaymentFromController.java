package org.example.controller.user;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.example.bo.BOFactory;
import org.example.bo.custom.PaymentBO;
import org.example.bo.custom.RegisterBO;
import org.example.dto.PaymentDTO;
import org.example.entity.Register;
import org.example.entity.tm.PaymentTm;

import java.util.List;

public class UserPaymentFromController {
    public AnchorPane rootNode;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnClear;
    public TableView<PaymentTm>tblPayment;
    public TableColumn<?,?> clmPaymentId;
    public TableColumn<?,?> clmRegisterId;
    public TableColumn<?,?> clmFullCourseFee;
    public TableColumn<?,?> clmAdvancedAmount;
    public TableColumn<?,?> clmRemainingAmount;
    public TableColumn<?,?> clmNewPayment;
    public TableColumn<?,?> clmBalance;
    public TextField txtPaymentId;
    public TextField txtAdvancedAmount;
    public TextField txtFullCourseFee;
    public TextField txtRemainingAmount;
    public TextField txtPaymentAmount;
    public Label lblBalance;
    public JFXComboBox<String> cmbRegisterId;

    PaymentBO paymentBO = (PaymentBO) BOFactory.getBOFactory().getBOType(BOFactory.BOType.Payment);
    RegisterBO registerBO = (RegisterBO) BOFactory.getBOFactory().getBOType(BOFactory.BOType.Register);

    public void initialize() {
        getRegisterId();
        generatePaymentId();
        setCellValueFactory();
        loadAllPayments();
    }
    private void loadAllPayments() {
        ObservableList<PaymentTm> paymentsTm = FXCollections.observableArrayList();
        List<PaymentDTO>paymentDTOS = paymentBO.getAll();
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
        clmRemainingAmount.setCellValueFactory(new PropertyValueFactory<>("paidAmount"));
        clmNewPayment.setCellValueFactory(new PropertyValueFactory<>("pay"));
        clmBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
    }

    private void generatePaymentId(){
        try {
            String currentId = paymentBO.getCurrentId();
            if (currentId != null) {
                String[] split = currentId.split("PAY");
                int id = Integer.parseInt(split[1]);
                txtPaymentId.setText("PAY00" + ++id);
            }else {
                txtPaymentId.setText("PAY001");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getRegisterId(){
        ObservableList<String> observableList = FXCollections.observableArrayList();
        try {
            List<String>registerIdList = registerBO.getIds();
            for (String registerId : registerIdList) {
                observableList.add(registerId);
            }
            cmbRegisterId.setItems(observableList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void cmbRegisterIdOnAcion(ActionEvent actionEvent) {
        String registerId = cmbRegisterId.getValue().toString();
        try {
            Register register = registerBO.searchByRegisterId(registerId);
            if (register != null) {
                int paidAmount = paymentBO.getPaidAmountByRegisterId(register);
                txtAdvancedAmount.setText(String.valueOf(paidAmount));

                int fullFee = paymentBO.getFullFeeAmountByRegisterId(register);
                txtFullCourseFee.setText(String.valueOf(fullFee));

                int remainingAmount = paymentBO.getRemainingFeeAmountByRegisterId(register);
                txtRemainingAmount.setText(String.valueOf(remainingAmount));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void btnPayOnAction(ActionEvent actionEvent) {
        try {
            String paymentId = txtPaymentId.getText();
            int advancedAmount = Integer.parseInt(txtAdvancedAmount.getText());
            int fullCourseFee = Integer.parseInt(txtFullCourseFee.getText());
            int remainingAmount = Integer.parseInt(txtRemainingAmount.getText());
            int payAmount = Integer.parseInt(txtPaymentAmount.getText());
            int balance = remainingAmount - payAmount;
            String registerId = cmbRegisterId.getValue();

            Register register = registerBO.searchByRegisterId(registerId);
            if (register == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Register Not Found");
                alert.setContentText("No register found with the provided ID.");
                alert.show();
                return;
            }

            if (payAmount <= 0 || payAmount > remainingAmount) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Payment Amount");
                alert.setHeaderText("Payment Error");
                alert.setContentText("Payment amount must be greater than 0 and less than or equal to the remaining amount.");
                alert.show();
                return;
            }

            boolean isSaved = paymentBO.save(new PaymentDTO(paymentId, advancedAmount, fullCourseFee, remainingAmount, payAmount, balance, register));
            if (isSaved) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Payment Success");
                alert.setHeaderText("Payment Processed");
                alert.setContentText("Payment has been successfully recorded.");
                alert.show();
                clearFields();
                generatePaymentId();
                loadAllPayments();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Payment Failure");
                alert.setHeaderText("Payment Not Processed");
                alert.setContentText("Something went wrong. Please try again.");
                alert.show();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Invalid Payment Details");
            alert.setContentText("Please enter valid numeric values for all fields.");
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }
    public void btnClearOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Clear Fields Confirmation");
        alert.setHeaderText("Are you sure you want to clear all fields?");
        alert.setContentText("This action cannot be undone.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                clearFields();
            }
        });
    }

    public void txtPaymentOnAction(ActionEvent actionEvent) {
        try {
            int remainingAmount = Integer.parseInt(txtRemainingAmount.getText());
            int paymentAmount = Integer.parseInt(txtPaymentAmount.getText());

            if (paymentAmount > remainingAmount || paymentAmount <= 0) {
                lblBalance.setText("Invalid Amount");
            } else {
                int newBalance = remainingAmount - paymentAmount;
                lblBalance.setText(String.valueOf(newBalance));
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Invalid Payment Amount");
            alert.setContentText("Please enter valid numeric values for payment and remaining amounts.");
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void clearFields(){
        cmbRegisterId.setValue("");
        txtAdvancedAmount.setText("");
        txtFullCourseFee.setText("");
        txtRemainingAmount.setText("");
        txtPaymentAmount.setText("");
        lblBalance.setText("");
    }
}
