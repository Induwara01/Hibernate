package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Course {
    @Id
    private String programId;
    private String programName;
    private String duration;
    private int fee;
    @ManyToOne
    @JoinColumn(name = "course programId",referencedColumnName = "programId")
    private Course course;

    public Course(String programId, String programName, String duration, int fee) {
    }


//    public Course(String programId, String programName, int duration, int fee) {
//        this.programId = programId;
//        this.programName = programName;
//        this.duration = duration;
//        this.fee = fee;
//    }
}
