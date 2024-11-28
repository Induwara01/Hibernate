package org.example.dao.custom;

import org.example.dao.CrudDAO;
import org.example.entity.Student;

import java.util.List;

public interface StudentDAO extends CrudDAO<Student> {
    List <String> getIds();
    String getCurrentId();
    Student searchByStudentId(String studentId);
    Student getObject(String value);
}
