package org.example.controller.admin;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.example.bo.BOFactory;
import org.example.bo.custom.RegisterBO;
import org.example.dto.RegisterDTO;
import org.example.entity.tm.RegisterTm;

import java.util.List;

public class AdminStudentProgramDetailsForm {
    public AnchorPane rootNode;
    public TableView<RegisterTm>tblEnrollmentDetails;
    public TableColumn<?,?> clmRegisterId;
    public TableColumn<?,?> clmStudentId;
    public TableColumn<?,?> clmProgramId;
    public TableColumn<?,?> clmAdvancedAmount;
    public TableColumn<?,?> clmDate;
    public TextField txtIds;
    public JFXButton btnSearch;

    RegisterBO registerBO = (RegisterBO) BOFactory.getBOFactory().getBOType(BOFactory.BOType.Register);

    public void initialize() {
        loadAllRegisters();
        setCellValueFactory();
    }
    private void loadAllRegisters(){
        ObservableList<RegisterTm> registerTms = FXCollections.observableArrayList();
        List<RegisterDTO> registerDTOS = registerBO.getAll();
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
        tblEnrollmentDetails.setItems(registerTms);
    }

    private void setCellValueFactory() {
        clmRegisterId.setCellValueFactory(new PropertyValueFactory<>("registerId"));
        clmAdvancedAmount.setCellValueFactory(new PropertyValueFactory<>("advanced"));
        clmDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        clmProgramId.setCellValueFactory(new PropertyValueFactory<>("programID"));
        clmStudentId.setCellValueFactory(new PropertyValueFactory<>("studentID"));
    }
    public void btnSearchOnAction(ActionEvent actionEvent) {
    }
}
