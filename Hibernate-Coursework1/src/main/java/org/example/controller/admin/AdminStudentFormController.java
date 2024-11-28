package org.example.controller.admin;

import com.jfoenix.controls.JFXButton;
import com.mysql.cj.xdevapi.Warning;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AdminStudentFormController {
    public AnchorPane rootNode;
    public TableView<StudentTm> tblStudent;
    public TableColumn<?,?> clmStudentId;
    public TableColumn<?,?> clmName;
    public TableColumn<?,?> clmAddress;
    public TableColumn<?,?> clmEmail;
    public TableColumn<?,?> clmTelephone;
    public TableColumn<?,?> clmDOB;
    public TextField txtIds;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnSearch;
    public Label lblDate;

    StudentBO studentBO = (StudentBO) BOFactory.getBOFactory().getBOType(BOFactory.BOType.Student);

    public void initialize() {
        setDateToLabel();
        setCellValueFactory();
        loadAllStudents();
        selectTableRow();
    }

    public void setDateToLabel() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        lblDate.setText(currentDate.format(formatter));
    }

    private void setCellValueFactory() {
        clmStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        clmAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        clmTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        clmDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
    }

    private void loadAllStudents() {
        ObservableList<StudentTm> studentTms = FXCollections.observableArrayList();
        List<StudentDTO> studentDTOS = studentBO.getAll();
        for (StudentDTO studentDTO : studentDTOS) {
            StudentTm studentTm = new StudentTm(studentDTO.getStudentId(),
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

    private void selectTableRow(){
        tblStudent.setOnMouseClicked(mouseEvent -> {
            int focusedIndex = tblStudent.getSelectionModel().getSelectedIndex();
            StudentTm studentTm = tblStudent.getItems().get(focusedIndex);
            txtIds.setText(studentTm.getStudentId());
        });
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Access Denied");
        alert.setHeaderText(null);
        alert.setContentText("You don't have access to perform this action.");
        alert.showAndWait();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try {
            boolean isDeleted = studentBO.delete(txtIds.getText());

            if (isDeleted) {
                loadAllStudents();
                tblStudent.refresh();
                new Alert(Alert.AlertType.INFORMATION, "Student Deleted Successfully!", ButtonType.OK).show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Student Deletion Failed!", ButtonType.OK).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage(), ButtonType.OK).show();
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String studentId = txtIds.getText();
        Student student = studentBO.search(studentId);

        if (student != null) {
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
            cleanFields();
        }else {
            new Alert(Alert.AlertType.ERROR,"Student Not Found!", ButtonType.OK).show();
        }
    }

    public void cleanFields(){
        txtIds.setText("");
    }
}
