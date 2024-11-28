package org.example.controller.user;

import com.jfoenix.controls.JFXButton;
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

public class UserCourseFormController {
    public AnchorPane rootNode;
    public TableView <CourseTm>tblCourse;
    public TableColumn <?,?>lblProgramId;
    public TableColumn <?,?>lblProgramName;
    public TableColumn <?,?>lblDuration;
    public TableColumn <?,?>lblFee;
    public JFXButton btnSearch;
    public TextField txtSearch;
    public Label lblDate;

    public void clearFields() {
        txtSearch.clear();
    }

    CourseBO courseBO = (CourseBO) BOFactory.getBOFactory().getBOType(BOFactory.BOType.Course);

    public void initialize() {
        setCellValueFactory();
        loadAllCourses();
    }
    private void setCellValueFactory() {
        lblProgramId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        lblProgramName.setCellValueFactory(new PropertyValueFactory<>("programName"));
        lblDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        lblFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
    }

    private void loadAllCourses() {
        ObservableList<CourseTm> courseTms = FXCollections.observableArrayList();
        List<CourseDTO>courseDTOS = courseBO.getAll();
        for (CourseDTO courseDTO : courseDTOS) {
            CourseTm courseTm = new CourseTm(courseDTO.getProgramId(), courseDTO.getProgramName(), courseDTO.getDuration(), courseDTO.getFee());
            courseTms.add(courseTm);
        }
        tblCourse.setItems(courseTms);
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
            clearFields();
        } else {
            new Alert(Alert.AlertType.ERROR, "Course not found!", ButtonType.CLOSE).show();
        }
    }
}
