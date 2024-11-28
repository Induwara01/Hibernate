package org.example.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@ToString
public class Register {
    @Id
    private String registerId;
    private int advanced;
    private LocalDate date;

    @ManyToOne
    private Course course;

    @ManyToOne(cascade = CascadeType.ALL)
    private Student student;
}
