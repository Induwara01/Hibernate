package org.example.controller.admin;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.bo.BOFactory;
import org.example.bo.custom.AdminBO;
import org.example.controller.LoginFormController;
import org.example.dto.AdminDTO;
import org.example.dto.UserDTO;
import org.example.entity.Admin;
import org.example.entity.User;
import org.mindrot.jbcrypt.BCrypt;

public class AdminSettingFormController {
    public AnchorPane rootNode;
    public TextField txtLoggedEmail;
    public TextField txtCurrentPassword;
    public TextField txtNewPassword;
    public TextField txtReEnterPassword;
    public JFXButton btnSave;
    public TextField txtUserName;
    public TextField txtTelephone;
    public JFXButton btnUpdate;
    public Label lblUserId;

    private Admin adminDTO;
    AdminBO adminBO = (AdminBO) BOFactory.getBOFactory().getBOType(BOFactory.BOType.Admin);

    public void initialize() {
        try {
            txtLoggedEmail.setText(LoginFormController.loggedUserEmail);
            txtUserName.setText(LoginFormController.loggedUserName);
            txtTelephone.setText(String.valueOf(LoginFormController.telephone));
            lblUserId.setText(LoginFormController.loggedUserId);

            adminDTO = adminBO.searchByEmail(LoginFormController.loggedUserEmail);

            if (adminDTO == null) {
                new Alert(Alert.AlertType.ERROR, "Admin details not found for the logged-in email.").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error loading user details: " + e.getMessage()).show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String email = txtLoggedEmail.getText();
        String password = txtCurrentPassword.getText();
        String newPassword = txtNewPassword.getText();
        String reEnterPassword = txtReEnterPassword.getText();

        // Validate that all fields are filled
        if (password.isEmpty() || newPassword.isEmpty() || reEnterPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "All fields are required.");
            return;
        }

        // Validate current password
        if (!BCrypt.checkpw(password, adminDTO.getPassword())) {
            showAlert(Alert.AlertType.ERROR, "Invalid Password", "The current password you entered is incorrect.");
            return;
        }

        // Validate new passwords match
        if (!newPassword.equals(reEnterPassword)) {
            showAlert(Alert.AlertType.ERROR, "Password Mismatch", "New passwords do not match. Please try again.");
            return;
        }

        try {
            // Hash the new password
            String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            // Create an updated AdminDTO
            AdminDTO updatedAdmin = new AdminDTO(
                    adminDTO.getUserId(),
                    txtUserName.getText(),
                    email,
                    hashedNewPassword,
                    Integer.parseInt(txtTelephone.getText())
            );

            // Update the admin using the adminBO
            boolean isUpdated = adminBO.update(updatedAdmin);

            // Notify user about the result
            if (isUpdated) {
                showAlert(Alert.AlertType.INFORMATION, "Update Successful", "Your password has been updated.");
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Update Failed", "Failed to update your password. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while updating your password: " + e.getMessage());
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String email = txtLoggedEmail.getText();
        String username = txtUserName.getText();
        String telephone = txtTelephone.getText();

        try {
            if (username.isEmpty() || telephone.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "All fields are required.");
                return;
            }

            int phoneNumber;
            try {
                phoneNumber = Integer.parseInt(telephone);
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Telephone must be a numeric value.");
                return;
            }

            AdminDTO updatedAdmin = new AdminDTO(
                    adminDTO.getUserId(),
                    username,
                    email,
                    adminDTO.getPassword(),
                    phoneNumber
            );

            boolean isUpdated = adminBO.update(updatedAdmin);

            if (isUpdated) {
                showAlert(Alert.AlertType.INFORMATION, "Update Successful", "User details updated successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Update Failed", "Failed to update user details.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // Optional
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        txtCurrentPassword.clear();
        txtNewPassword.clear();
        txtReEnterPassword.clear();
        txtUserName.clear();
        txtTelephone.clear();
    }
}
