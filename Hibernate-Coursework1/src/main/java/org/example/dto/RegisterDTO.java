package org.example.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.Course;
import org.example.entity.Student;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterDTO {
    @Id
    private String registerId;
    private int advanced;
    private LocalDate date;
    private Course course;
    private Student student;

    public RegisterDTO(String registerId, Student student, Course course, int advanced, LocalDate date) {
        this.registerId = registerId;
        this.student = student;
        this.course = course;
        this.advanced = advanced;
        this.date = date;
    }
}
