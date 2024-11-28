package org.example.controller.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.example.bo.BOFactory;
import org.example.bo.custom.CourseBO;
import org.example.dto.CourseDTO;
import org.example.entity.Course;
import org.example.entity.tm.CourseTm;

import java.util.List;

public class CourseFormController {
    public AnchorPane rootNode;
    public TableView <CourseTm>tblCourse;
    public TableColumn <?,?>clmProgramId;
    public TableColumn <?,?>clmProgramName;
    public TableColumn <?,?>clmDuration;
    public TableColumn <?,?>clmFee;
    public TextField txtProgramId;
    public TextField txtProgramName;
    public TextField txtDuration;
    public TextField txtFee;
    public TextField txtSearch;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnClear;
    public Button btnSearch;

    CourseBO courseBO = (CourseBO) BOFactory.getBOFactory().getBOType(BOFactory.BOType.Course);

    public void initialize() {
        generateCourseId();
        loadAllCoursers();
        setCellValueFactory();
        selectTableRow();
    }

    private void selectTableRow(){
        tblCourse.setOnMouseClicked(mouseEvent -> {
            int focusIndex = tblCourse.getFocusModel().getFocusedIndex();
            CourseTm courseTm = tblCourse.getItems().get(focusIndex);
            txtProgramId.setText(courseTm.getProgramId());
            txtProgramName.setText(courseTm.getProgramName());
            txtDuration.setText(courseTm.getDuration());
            txtFee.setText(String.valueOf(courseTm.getFee()));
        });
    }

    void clearTextFields(){
        txtProgramId.clear();
        txtProgramName.clear();
        txtDuration.clear();
        txtFee.clear();
    }

    private String generateCourseId(){
        try {
            String currentId = courseBO.getCurrentId();
            if (currentId != null){
                String[] slipt = currentId.split("CA100");
                int id = Integer.parseInt(slipt[1]);
                String avaliableId = "CA100" + ++id;
                txtProgramId.setText(avaliableId);
                return avaliableId;
            }else {
                txtProgramId.setText("CA1001");
                return "CA1001";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private void setCellValueFactory(){
        clmProgramId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        clmProgramName.setCellValueFactory(new PropertyValueFactory<>("programName"));
        clmDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        clmFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
    }

    private void loadAllCoursers(){
        ObservableList<CourseTm> courseTms = FXCollections.observableArrayList();
        List<CourseDTO>courseDTOS = courseBO.getAll();
        for (CourseDTO courseDTO : courseDTOS) {
            CourseTm courseTm = new CourseTm(courseDTO.getProgramId(), courseDTO.getProgramName(), courseDTO.getDuration(), courseDTO.getFee());
            courseTms.add(courseTm);
        }
        tblCourse.setItems(courseTms);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        boolean isSaved = courseBO.save(new CourseDTO(txtProgramId.getText(),txtProgramName.getText(),txtDuration.getText(),Integer.parseInt(txtFee.getText())));
        if (isSaved) {
            clearTextFields();
            loadAllCoursers();
            new Alert(Alert.AlertType.INFORMATION, "Course Added Succesfully!", ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.INFORMATION, "Course Added Failed!", ButtonType.OK).show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        boolean isUpdated = courseBO.update(new CourseDTO(txtProgramId.getText(),txtProgramName.getText(),txtDuration.getText(),Integer.parseInt(txtFee.getText())));
        if (isUpdated) {
            clearTextFields();
            loadAllCoursers();
            setCellValueFactory();
            tblCourse.refresh();
            txtProgramId.setText(generateCourseId());
            new Alert(Alert.AlertType.INFORMATION, "Course Updated Succesfully!", ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Course Updated Failed!", ButtonType.OK).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        boolean isDeleted = courseBO.delete(txtProgramId.getText());
        if (isDeleted) {
            clearTextFields();
            loadAllCoursers();
            tblCourse.refresh();
            txtProgramId.setText(generateCourseId());
            new Alert(Alert.AlertType.INFORMATION, "Course Deleted Successfully!", ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Course Deleted Failed!", ButtonType.OK).show();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearTextFields();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String programId = txtSearch.getText();
        Course course = courseBO.search(programId);

        if (course != null) {
            String courseDetails = "Program ID: " + course.getProgramId() + "\n" +
                    "Program Name: " + course.getProgramName() + "\n" +
                    "Duration: " + course.getDuration() + "\n" +
                    "Fee: " + course.getFee();

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Course found!", ButtonType.OK);
            TextArea detailsArea = new TextArea(courseDetails);
            detailsArea.setEditable(false);
            detailsArea.setWrapText(true);

            detailsArea.setPrefSize(300, 150);

            alert.getDialogPane().setContent(detailsArea);
            alert.showAndWait();
            clearTextFields();
        } else {
            new Alert(Alert.AlertType.ERROR, "Course not found!", ButtonType.CLOSE).show();
        }
    }
}
