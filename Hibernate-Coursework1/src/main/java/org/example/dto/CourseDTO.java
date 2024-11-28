package org.example.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class CourseDTO {
    @Id
    private String programId;
    private String programName;
    private String duration;
    private int fee;
}
