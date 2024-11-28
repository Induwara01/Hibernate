package org.example.controller.user;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class BackToDashbardFormController {
    public AnchorPane rootNode;
    public Label lblStudent;
    public Label lblCourse;
    public Label lblDate;
    public Label lblTime;

    public void initialize() {
        setDate();
        updateTime();
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
}
