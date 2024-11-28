package org.example.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class StudentDTO {
    @Id
    private String studentId;
    private String studentName;
    private String address;
    private String email;
    private int telephone;
    private String dob;
}
