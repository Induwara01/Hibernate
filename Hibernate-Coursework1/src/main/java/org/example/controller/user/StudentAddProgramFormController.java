package org.example.controller.user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.example.bo.BOFactory;
import org.example.bo.custom.CourseBO;
import org.example.bo.custom.RegisterBO;
import org.example.bo.custom.StudentBO;
import org.example.dto.RegisterDTO;
import org.example.entity.Course;
import org.example.entity.Student;
import org.example.entity.tm.RegisterTm;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StudentAddProgramFormController {
    public AnchorPane rootNode;
    public Label lblRegisterId;
    public Label lblDate;
    public JFXComboBox <String>cmbStudentId;
    public Label lblStudentName;
    public Label lblEmail;
    public Label lblTelephone;
    public JFXComboBox cmbProgramID;
    public Label lblProgramName;
    public Label lblDuration;
    public Label lblFee;
    public TableView <RegisterTm>tblRegistationDetails;
    public TextField txtAdvancedAmount;
    public Label lblTime;
    public JFXButton btnClear;
    public TableColumn <?,?>clmRegisterID;
    public TableColumn <?,?>clmStudentID;
    public TableColumn <?,?>clmProgramID;
    public TableColumn <?,?>clmAdvancedAmount;
    public TableColumn <?,?>clmDate;
    public JFXButton btnSave;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnMakePayment;

    CourseBO courseBO = (CourseBO) BOFactory.getBOFactory().getBOType(BOFactory.BOType.Course);
    StudentBO studentBO = (StudentBO) BOFactory.getBOFactory().getBOType(BOFactory.BOType.Student);
    RegisterBO registerBO = (RegisterBO) BOFactory.getBOFactory().getBOType(BOFactory.BOType.Register);

    public void initialize() {
        setDate();
        updateTime();
        generateRegisterId();
        getStudentId();
        getProgramID();
        setCellValueFactory();
        loadAllRegisters();
    }
    private void loadAllRegisters(){
        ObservableList<RegisterTm> registerTms = FXCollections.observableArrayList();
        List<RegisterDTO>registerDTOS = registerBO.getAll();
        for (RegisterDTO registerDTO : registerDTOS) {
            RegisterTm registerTm = new RegisterTm(
                                registerDTO.getRegisterId(),
                                registerDTO.getAdvanced(),
                                registerDTO.getDate(),
                    registerDTO.getCourse(),
                    registerDTO.getStudent()
                        );
            registerTms.add(registerTm);
        }
        tblRegistationDetails.setItems(registerTms);
    }

    private void setCellValueFactory() {
        clmRegisterID.setCellValueFactory(new PropertyValueFactory<>("registerId"));
        clmAdvancedAmount.setCellValueFactory(new PropertyValueFactory<>("advanced"));
        clmDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        clmProgramID.setCellValueFactory(new PropertyValueFactory<>("programID"));
        clmStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
    }
    private void setDate() {
        LocalDate now = LocalDate.now();
        lblDate.setText(String.valueOf(now));
    }
    private void updateTime() {
        LocalTime currentTime = LocalTime.now();
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        lblTime.setText(formattedTime);
    }
    private void getStudentId(){
        ObservableList<String>obList = FXCollections.observableArrayList();
        try {
            List<String>studentIdList = studentBO.getIds();
            for (String studentId : studentIdList) {
                obList.add(studentId);
            }
            cmbStudentId.setItems(obList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void getProgramID(){
        ObservableList<String>obList = FXCollections.observableArrayList();
        try {
            List<String>programIdList = courseBO.getIds();
            for (String programId : programIdList) {
                obList.add(programId);
            }
            cmbProgramID.setItems(obList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void cmbStudentIdOnAction(ActionEvent actionEvent) {
//        List<Student>students = registerBO.getStudentId();
//        cmbProgramID.getItems().addAll(students);
        String studentId = cmbStudentId.getValue().toString();
        try {
            Student student = studentBO.searchByStudentId(studentId);
            if (student != null) {
                lblStudentName.setText(student.getStudentName());
                lblEmail.setText(student.getEmail());
                lblTelephone.setText(String.valueOf(student.getTelephone()));
            }
            cmbProgramID.requestFocus();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void cmbProgrmIdOnAction(ActionEvent actionEvent) {
//        List<Course>courses = registerBO.getProgramId();
//        cmbStudentId.getItems().addAll(courses);
        String programId = cmbProgramID.getValue().toString();
        try {
            Course course = courseBO.searchByProgrameId(programId);
            if (course != null) {
                lblProgramName.setText(course.getProgramName());
                lblDuration.setText(course.getDuration());
                lblFee.setText(String.valueOf(course.getFee()));
            }
            txtAdvancedAmount.requestFocus();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void btnClearOnAction(ActionEvent actionEvent) {
    }


    public void btnSaveOnAction(ActionEvent actionEvent) {
        try {
            String registerId = lblRegisterId.getText();
            String studentId = cmbStudentId.getValue().toString();
            String programId = cmbProgramID.getValue().toString();
            int advancedAmount = Integer.parseInt(txtAdvancedAmount.getText());
            LocalDate date = LocalDate.now();

            Student student = studentBO.searchByStudentId(studentId);
            Course course = courseBO.searchByProgrameId(programId);

            if (student != null && course != null) {
                RegisterDTO registerDTO = new RegisterDTO(registerId, student, course, advancedAmount, date);
                boolean isSaved = registerBO.save(registerDTO);
                if (isSaved) {
                    loadAllRegisters();
                    clearFields();
                    lblRegisterId.setText(generateRegisterId());
                    System.out.println("Ai naththe");
                    new Alert(Alert.AlertType.CONFIRMATION, "Student Registered Successfully").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Student Registration Failed").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Invalid Student or Course Selection").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        new Alert(Alert.AlertType.WARNING, "you don't have access!").show();
    }
    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }

    private String generateRegisterId() {
        try {
            String currentId = registerBO.currentId();
            if (currentId != null) {
                String[] split = currentId.split("REG");
                int id = Integer.parseInt(split[1]);
                String availableId = String.format("REG%04d", ++id);
                lblRegisterId.setText(availableId);
                return availableId;
            } else {
                lblRegisterId.setText("REG0001");
                return "REG0001";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void btnMakePymentOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/user/user-payment-form.fxml"));
        rootNode.getChildren().clear();
        rootNode.getChildren().add(anchorPane);
    }

    public void clearFields(){
        lblStudentName.setText("");
        lblEmail.setText("");
        lblTelephone.setText("");
        txtAdvancedAmount.setText("");
        cmbProgramID.setDisable(false);
        cmbStudentId.setDisable(false);
        lblFee.setText("");
        lblDuration.setText("");
        lblProgramName.setText("");
    }

}
