package org.example.bo.custom;

import org.example.bo.SuperBo;
import org.example.dto.StudentDTO;
import org.example.entity.Student;

import java.util.List;

public interface StudentBO extends SuperBo {
    public boolean save(StudentDTO studentDTO);
    public boolean update(StudentDTO studentDTO);
    public boolean delete(String id);
    public Student search(String id);
    public StudentDTO get(String value);

    List<StudentDTO>getAll();
    List<String>getIds();
    String getCurrentId();
    Student searchByStudentId(String studentId);
}
