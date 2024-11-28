package org.example.dao.custom;

import org.example.dao.CrudDAO;
import org.example.entity.Course;
import org.example.entity.Register;
import org.example.entity.Student;

import java.util.List;

public interface RegisterDAO extends CrudDAO<Register> {
    List<Course> getProgramId();
    List<Student> getStudentId();
    String getCurrentId();
    List<String>getIds();
    Register searchByRegisterId(String registerId);
}
