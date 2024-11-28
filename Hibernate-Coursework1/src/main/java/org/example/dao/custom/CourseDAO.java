package org.example.dao.custom;

import org.example.dao.CrudDAO;
import org.example.entity.Course;

import java.util.List;

public interface CourseDAO extends CrudDAO<Course> {
    List<String> getIds();
    String getCurrentId();
    Course searchByProgramId(String programId);
    Course getObject(String value);
}
