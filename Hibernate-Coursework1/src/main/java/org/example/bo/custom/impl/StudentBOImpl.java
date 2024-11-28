package org.example.bo.custom.impl;

import org.example.bo.custom.StudentBO;
import org.example.config.FactoryConfiguration;
import org.example.dao.DAOFactory;
import org.example.dao.custom.StudentDAO;
import org.example.dao.custom.impl.StudentDAOImpl;
import org.example.dto.CourseDTO;
import org.example.dto.StudentDTO;
import org.example.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentBOImpl implements StudentBO {
    StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAOType(DAOFactory.DAOType.Student);
    @Override
    public boolean save(StudentDTO studentDTO) {
        return studentDAO.save(new Student(studentDTO.getStudentId(),studentDTO.getStudentName(),studentDTO.getAddress(),studentDTO.getEmail(),studentDTO.getTelephone(),studentDTO.getDob()));
    }

    @Override
    public boolean update(StudentDTO studentDTO) {
        return studentDAO.update(new Student(studentDTO.getStudentId(),studentDTO.getStudentName(),studentDTO.getAddress(),studentDTO.getEmail(),studentDTO.getTelephone(),studentDTO.getDob()));
    }

    @Override
    public boolean delete(String id) {
        return studentDAO.delete(id);
    }

    @Override
    public Student search(String id) {
        return studentDAO.search(id);
    }

    @Override
    public StudentDTO get(String value) {
        return null;
    }

    @Override
    public List<StudentDTO> getAll() {
        List<StudentDTO> studentDTOS = new ArrayList<>();
        List<Student> students = studentDAO.getAll();
        for (Student student : students) {
            studentDTOS.add(new StudentDTO(student.getStudentId(),
                    student.getStudentName(),
                    student.getAddress(),
                    student.getEmail(),
                    student.getTelephone(),
                    student.getDob()));
        }
        return studentDTOS;
    }

    @Override
    public List<String> getIds() {
        return studentDAO.getIds();
    }

    @Override
    public String getCurrentId() {
        return studentDAO.getCurrentId();
    }

    @Override
    public Student searchByStudentId(String studentId) {
        return studentDAO.searchByStudentId(studentId);
    }
}
