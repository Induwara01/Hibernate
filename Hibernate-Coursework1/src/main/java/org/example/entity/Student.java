package org.example.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Student {
    @Id
    private String studentId;
    private String studentName;
    private String address;
    private String email;
    private int telephone;
    private String dob;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Register>registers;

//    public Student(String studentId, String studentName, String address, String email, int telephone, String dob) {
//    }

    public Student(String studentId, String studentName, String address, String email, int telephone, String dob) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.address = address;
        this.email = email;
        this.telephone = telephone;
        this.dob = dob;
    }
}
