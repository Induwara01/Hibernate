package org.example.bo.custom.impl;

import org.example.bo.custom.CourseBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.CourseDAO;
import org.example.dto.CourseDTO;
import org.example.entity.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseBOImpl implements CourseBO {
    CourseDAO courseDAO = (CourseDAO) DAOFactory.getDaoFactory().getDAOType(DAOFactory.DAOType.Course);
    @Override
    public boolean save(CourseDTO courseDTO) {
        return courseDAO.save(new Course(courseDTO.getProgramId(), courseDTO.getProgramName(),courseDTO.getDuration(),courseDTO.getFee()));
    }

    @Override
    public boolean update(CourseDTO courseDTO) {
        return courseDAO.update(new Course(courseDTO.getProgramId(), courseDTO.getProgramName(),courseDTO.getDuration(),courseDTO.getFee()));
    }

    @Override
    public boolean delete(String id) {
        return courseDAO.delete(id);
    }

    @Override
    public Course search(String id) {
        return courseDAO.search(id);
    }

    @Override
    public CourseDTO get(String value) {
        return null;
    }

    @Override
    public List<CourseDTO> getAll() {
        List<CourseDTO> courseDTOS = new ArrayList<>();
        List<Course> courses = courseDAO.getAll();
        for (Course course : courses) {
            courseDTOS.add(new CourseDTO(course.getProgramId(), course.getProgramName(), course.getDuration(), course.getFee()));
        }
        return courseDTOS;
    }

    @Override
    public List<String> getIds() {
        return courseDAO.getIds();
    }

    @Override
    public String getCurrentId() {
        return courseDAO.getCurrentId();
    }

    @Override
    public Course searchByProgrameId(String programeId) {
        return courseDAO.searchByProgramId(programeId);
    }
}
