package org.example.entity.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.Course;
import org.example.entity.Student;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterTm {
    private String registerId;
    private int advanced;
    private LocalDate date;
    private Course course;
    private Student student;

    public RegisterTm(String registerId, Student student, Course courses, int advanced, LocalDate date) {
        this.registerId = registerId;
        this.student = student;
        this.course = courses;
        this.advanced = advanced;
        this.date = date;
    }

    // Add this method to expose the studentID
    public String getStudentID() {
        return student != null ? student.getStudentId() : null;
    }

    // Add this method to expose the programID
    public String getProgramID() {
        return course != null ? course.getProgramId() : null;
    }
}