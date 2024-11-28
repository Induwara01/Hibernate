package org.example.controller.user;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.example.bo.BOFactory;
import org.example.bo.custom.StudentBO;
import org.example.dto.StudentDTO;
import org.example.entity.Student;
import org.example.entity.tm.StudentTm;
import org.example.validation.Regex;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StudentFormController {
    public AnchorPane rootNode;
    public TableView <StudentTm>tblStudent;
    public TableColumn <?,?>clmStudentId;
    public TableColumn <?,?>clmName;
    public TableColumn <?,?>clmAddress;
    public TableColumn <?,?>clmEmail;
    public TableColumn <?,?>clmTelephone;
    public TableColumn <?,?>clmDob;
    public TextField txtStudentId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtEmail;
    public TextField txtTelephone;
    public TextField txtDob;
    public JFXButton btnAddToCourse;
    public JFXButton btnSave;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnClear;
    public JFXButton btnSearch;
    public TextField txtSearch;

    StudentBO studentBO = (StudentBO) BOFactory.getBOFactory().getBOType(BOFactory.BOType.Student);

    public void initialize() {
        generateStudentId();
        setCellValueFactory();
        loadAllStudents();
        selectTableRow();
        generateStudentId();
    }

    private void selectTableRow(){
        tblStudent.setOnMouseClicked(mouseEvent -> {
            int focusedIndex = tblStudent.getSelectionModel().getSelectedIndex();
            StudentTm studentTm = tblStudent.getItems().get(focusedIndex);
            txtStudentId.setText(studentTm.getStudentId());
            txtName.setText(studentTm.getStudentName());
            txtAddress.setText(studentTm.getAddress());
            txtEmail.setText(studentTm.getEmail());
            txtTelephone.setText(String.valueOf(studentTm.getTelephone()));
            txtDob.setText(String.valueOf(studentTm.getDob()));
        });
    }

    public void cleanTextFields() {
        txtStudentId.clear();
        txtName.clear();
        txtAddress.clear();
        txtEmail.clear();
        txtTelephone.clear();
        txtDob.clear();
    }

    private String generateStudentId() {
        try {
            String currentId = studentBO.getCurrentId();
            if (currentId != null) {
                String[] split = currentId.split("S");
                int id = Integer.parseInt(split[1]);
                String availableId = String.format("S%05d", ++id);
                txtStudentId.setText(availableId);
                return availableId;
            } else {
                txtStudentId.setText("S00001");
                return "S00001";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setCellValueFactory() {
        clmStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        clmAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        clmTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        clmDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
    }

    private void loadAllStudents() {
        ObservableList<StudentTm> studentTms = FXCollections.observableArrayList();
        List<StudentDTO>studentDTOS = studentBO.getAll();
        for (StudentDTO studentDTO : studentDTOS) {
            StudentTm studentTm = new StudentTm(
                    studentDTO.getStudentId(),
                    studentDTO.getStudentName(),
                    studentDTO.getAddress(),
                    studentDTO.getEmail(),
                    studentDTO.getTelephone(),
                    studentDTO.getDob()
            );
            studentTms.add(studentTm);
        }
        tblStudent.setItems(studentTms);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        if (isValid()) {
            boolean isSaved = studentBO.save(new StudentDTO(
                    txtStudentId.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    txtEmail.getText(),
                    Integer.parseInt(txtTelephone.getText()),
                    txtDob.getText()
            ));

            if (isSaved) {
                cleanTextFields();
                loadAllStudents();
                txtStudentId.setText(generateStudentId());
                new Alert(Alert.AlertType.INFORMATION, "Student Added Successfully!", ButtonType.OK).show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Student Adding Failed!", ButtonType.OK).show();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        boolean isUpdated = studentBO.update(new StudentDTO(txtStudentId.getText(),txtName.getText(),txtAddress.getText()
        ,txtEmail.getText(),Integer.parseInt(txtTelephone.getText()),txtDob.getText()));
        if (isUpdated) {
            cleanTextFields();
            loadAllStudents();
            tblStudent.refresh();
            txtStudentId.setText(generateStudentId());
            new Alert(Alert.AlertType.INFORMATION,"Student Updated Successfully!", ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Student Updated Failed!", ButtonType.OK).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try {
            boolean isDeleted = studentBO.delete(txtStudentId.getText());

            if (isDeleted) {
                cleanTextFields();
                loadAllStudents();
                tblStudent.refresh();
                txtStudentId.setText(generateStudentId());
                new Alert(Alert.AlertType.INFORMATION, "Student Deleted Successfully!", ButtonType.OK).show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Student Deletion Failed!", ButtonType.OK).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage(), ButtonType.OK).show();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        cleanTextFields();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String studentId = txtSearch.getText();
        Student student = studentBO.search(studentId);

        if (student != null) {
            txtStudentId.setText(student.getStudentId());
            txtName.setText(student.getStudentName());
            txtAddress.setText(student.getAddress());
            txtEmail.setText(student.getEmail());
            txtTelephone.setText(String.valueOf(student.getTelephone()));
            txtDob.setText(String.valueOf(student.getDob()));
            System.out.println("Student Found!");

            String studentDetails = "Student ID: " + student.getStudentId() + "\n" +
                    "Student Name: " + student.getStudentName() + "\n" +
                    "Address: " + student.getAddress() + "\n" +
                    "Email: " + student.getEmail() + "\n" +
                    "Telephone: " + student.getTelephone() + "\n" +
                    "Dob: " + student.getDob();

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Student Found!", ButtonType.OK);
            TextArea detailsArea = new TextArea(studentDetails);
            detailsArea.setEditable(false);
            detailsArea.setWrapText(true);

            detailsArea.setPrefSize(300,150);

            alert.getDialogPane().setContent(detailsArea);
            alert.showAndWait();
            cleanTextFields();
        }else {
            new Alert(Alert.AlertType.ERROR,"Student Not Found!", ButtonType.OK).show();
        }
    }

    private boolean isValid() {
        // Validate name
        if (txtName.getText().isEmpty() || !txtName.getText().matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Name! Name should only contain alphabets and spaces.", ButtonType.OK).show();
            txtName.requestFocus();
            return false;
        }

        // Validate address
        if (txtAddress.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Address cannot be empty!", ButtonType.OK).show();
            txtAddress.requestFocus();
            return false;
        }

        // Validate email
        if (!txtEmail.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            setErrorStyle(txtEmail);
            new Alert(Alert.AlertType.ERROR, "Invalid Email Address!", ButtonType.OK).show();
            txtEmail.requestFocus();
            return false;
        }else {
            resetStyle(txtEmail);
        }

        // Validate telephone
        if (!txtTelephone.getText().matches("\\d{10}")) {
            setErrorStyle(txtTelephone);
            new Alert(Alert.AlertType.ERROR, "Invalid Telephone Number! Telephone should be 10 digits.", ButtonType.OK).show();
            txtTelephone.requestFocus();
            return false;
        }else {
            resetStyle(txtTelephone);
        }

        // Validate date of birth (DOB)
        if (txtDob.getText().isEmpty() || !txtDob.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
            new Alert(Alert.AlertType.ERROR, "Invalid DOB! Use the format YYYY-MM-DD.", ButtonType.OK).show();
            txtDob.requestFocus();
            return false;
        }

        return true;
    }

    private void setErrorStyle(TextField textField) {
        textField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
    }

    private void resetStyle(TextField textField) {
        textField.setStyle(null);
    }
}
