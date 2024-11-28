package org.example.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.bo.BOFactory;
import org.example.bo.custom.AdminBO;
import org.example.bo.custom.UserBO;
import org.example.dto.AdminDTO;
import org.example.dto.UserDTO;
import org.example.validation.Regex;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class RegisterFormController {
    public AnchorPane node;
    public TextField txtName;
    public TextField txtTele;
    public TextField txtEmail;
    public PasswordField txPassword;
    public JFXButton btnRegistre;
    public Hyperlink hyperLogIn;
    public TextField txtId;
    public JFXComboBox <String>cmbAccountType;
    public PasswordField txtConfirmPassword;

    UserBO userBO = (UserBO) BOFactory.getBOFactory().getBOType(BOFactory.BOType.User);
    AdminBO adminBO = (AdminBO) BOFactory.getBOFactory().getBOType(BOFactory.BOType.Admin);

    public void initialize() {
        cmbAccountType.getItems().addAll("User","Admin");
    }

    private String genarateUserId(){
        try {
            String currentId = userBO.getCurrentId();
            if (currentId == null) {
                String[] split = currentId.split("SU");
                int id = Integer.parseInt(split[1]);
                String avliableId = String.format("SU%04d", ++id);
                txtId.setText(avliableId);
                return avliableId;
            }else {
                txtId.setText("SU0001");
                return "SU0001";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void btnRegisterOnAction(ActionEvent actionEvent) {
        boolean isTeleValid = validateTelephone(txtTele);
        boolean isPasswordValid = validatePassword(txPassword);

        if (!isTeleValid || !isPasswordValid) {
            new Alert(Alert.AlertType.ERROR, "Please correct the highlighted fields").show();
            return;
        }

        String id = txtId.getText();
        String name = txtName.getText();
        String teleStr = txtTele.getText();
        String type = cmbAccountType.getValue();
        String email = txtEmail.getText();
        String password = txPassword.getText();
        String rePassword = txtConfirmPassword.getText();

        if (id.isEmpty() || name.isEmpty() || teleStr.isEmpty() || email.isEmpty() || password.isEmpty() || rePassword.isEmpty() || type == null) {
            new Alert(Alert.AlertType.ERROR, "All fields are required").show();
            return;
        }

        int tele;
        try {
            tele = Integer.parseInt(teleStr);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid telephone number").show();
            return;
        }

        if (!password.equals(rePassword)) {
            new Alert(Alert.AlertType.ERROR, "Passwords do not match").show();
            return;
        }

        if (!Regex.validateEmail(email)) {
            new Alert(Alert.AlertType.ERROR, "Invalid email or password format").show();
            return;
        }

        try {
            if (type.equals("Admin")) {
                adminBO.save(new AdminDTO(id, name, tele, email, password));
            } else if (type.equals("User")) {
                userBO.save(new UserDTO(id,name,tele,email,password));
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid account type selected").show();
                return;
            }

            cleanFields();
            new Alert(Alert.AlertType.INFORMATION, "Successfully registered").show();

            txtId.setText(genarateUserId());
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Registration failed: " + e.getMessage()).show();
        }
    }

    public void hyperLogInOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/login-form.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = (Stage)this.node.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Form");
    }

    public void cmbAccountTypeOnAction(ActionEvent actionEvent) {
    }

    private void cleanFields() {
        txtName.clear();
        txtTele.clear();
        txtEmail.clear();
        txPassword.clear();
        txtConfirmPassword.clear();
    }

    private boolean validateTelephone(TextField textField) {
        String input = textField.getText();
        boolean isValid = input.matches("\\d{10}");
        updateTextFieldStyle(textField, isValid);
        return isValid;
    }

    private boolean validatePassword(TextField textField) {
        String input = textField.getText();
        boolean isValid = input.length() >= 6;
        updateTextFieldStyle(textField, isValid);
        return isValid;
    }
//    public static boolean validatePassword(String password) {
//    String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
//    Pattern pat = Pattern.compile(passwordRegex);
//    if (password == null)
//        return false;
//    return pat.matcher(password).matches();
//    }

    private void updateTextFieldStyle(TextField textField, boolean isValid) {
        textField.getStyleClass().removeAll("text-field-valid", "text-field-invalid");
        if (isValid) {
            textField.getStyleClass().add("text-field-valid");
        } else {
            textField.getStyleClass().add("text-field-invalid");
        }
    }
}
