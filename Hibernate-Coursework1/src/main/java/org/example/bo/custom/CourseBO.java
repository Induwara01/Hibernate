package org.example.bo.custom;

import org.example.bo.SuperBo;
import org.example.dto.CourseDTO;
import org.example.entity.Course;

import java.util.List;

public interface CourseBO extends SuperBo {
    public boolean save(CourseDTO courseDTO);
    public boolean update(CourseDTO courseDTO);
    public boolean delete(String id);
    public Course search(String id);
    public CourseDTO get(String value);

    List<CourseDTO> getAll();
    List<String> getIds();
    String getCurrentId();
    Course searchByProgrameId(String programeId);
}
