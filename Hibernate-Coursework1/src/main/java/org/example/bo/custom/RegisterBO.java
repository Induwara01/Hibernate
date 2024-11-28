package org.example.bo.custom;

import org.example.bo.SuperBo;
import org.example.dto.RegisterDTO;
import org.example.entity.Course;
import org.example.entity.Register;
import org.example.entity.Student;

import java.util.List;

public interface RegisterBO extends SuperBo {
    boolean save(RegisterDTO registerDTO);
    boolean update(RegisterDTO registerDTO);
    boolean delete(RegisterDTO registerDTO);
    boolean search(String id);
    String currentId();
    List<RegisterDTO> getAll();
    List<Course>getProgramId();
    List<Student>getStudentId();
    List<String>getIds();
    Register searchByRegisterId(String registerId);
}
