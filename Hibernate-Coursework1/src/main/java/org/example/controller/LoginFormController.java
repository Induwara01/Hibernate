package org.example.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.bo.BOFactory;
import org.example.bo.custom.AdminBO;
import org.example.bo.custom.UserBO;
import org.example.dto.AdminDTO;
import org.example.dto.UserDTO;
import org.example.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.List;

public class LoginFormController {
    public AnchorPane rootNode;
    public TextField txtuserName;
    public PasswordField txtpassword;
    public JFXButton btnLogin;
    public Hyperlink hyperRegister;
    public AnchorPane node;
    public JFXComboBox <String>cmbAccountType;

    private static String email;
    public static String loggedUserEmail;
    public static String loggedUserName;
    public static String loggedUserId;
    public static int telephone;

    UserBO userBO = (UserBO) BOFactory.getBOFactory().getBOType(BOFactory.BOType.User);
    AdminBO adminBO = (AdminBO) BOFactory.getBOFactory().getBOType(BOFactory.BOType.Admin);

    public void initialize() {
        cmbAccountType.getItems().addAll("User", "Admin");
    }

    public void btnLoginOnAction(ActionEvent actionEvent) {
        String userName = txtuserName.getText();
        String password = txtpassword.getText();
        String type = cmbAccountType.getValue();

        if (type == null || userName.isEmpty() || password.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill in all fields and select account type.").show();
            return;
        }

        try {
            boolean isCredentialOk = false;

            if (type.equals("User")) {
                List<UserDTO> allUsers = userBO.getAll();
                for (UserDTO userDTO : allUsers) {
                    if (userDTO.getUserName().equals(userName) && BCrypt.checkpw(password, userDTO.getPassword())) {
                        // Password matches
                        isCredentialOk = true;
                        loggedUserEmail = userDTO.getEmail();
                        loggedUserName = userDTO.getUserName();
                        loggedUserId = userDTO.getUserId();
                        telephone = userDTO.getTelephone();

                        showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + userDTO.getUserName());

                        // Load User Dashboard
                        loadScene("/view/user/dashboard-form.fxml", "Welcome " + userDTO.getUserName());
                        break;
                    }
                }
            } else if (type.equals("Admin")) {
                List<AdminDTO> allAdmins = adminBO.getAll();
                for (AdminDTO adminDTO : allAdmins) {
                    if (adminDTO.getUserName().equals(userName) && BCrypt.checkpw(password, adminDTO.getPassword())) {
                        // Password matches
                        isCredentialOk = true;
                        loggedUserEmail = adminDTO.getEmail();
                        loggedUserName = adminDTO.getUserName();
                        loggedUserId = adminDTO.getUserId();
                        telephone = adminDTO.getTelephone();

                        showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + adminDTO.getUserName());

                        // Load Admin Dashboard
                        loadScene("/view/admin/admin-dashboard-form.fxml", "Welcome " + adminDTO.getUserName());
                        break;
                    }
                }
            }

            if (!isCredentialOk) {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while logging in. Please try again.");
        }
    }

    private void loadScene(String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Scene scene = new Scene(root);

            // Get current stage
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            if (stage != null) {
                stage.setScene(scene);
                stage.setTitle(title);
                stage.centerOnScreen();
                stage.show();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Unable to load the stage.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to load the requested scene.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // Optional
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void hyperRegisterOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/register-form.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = (Stage)this.node.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Registation Form");
    }
}
