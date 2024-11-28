package org.example.bo.custom.impl;

import org.example.bo.custom.RegisterBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.RegisterDAO;
import org.example.dao.custom.impl.RegisterDAOImpl;
import org.example.dto.RegisterDTO;
import org.example.entity.Course;
import org.example.entity.Register;
import org.example.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class RegisterBOImpl implements RegisterBO {
    RegisterDAO registerDAO = (RegisterDAO) DAOFactory.getDaoFactory().getDAOType(DAOFactory.DAOType.Register);
    @Override
    public boolean save(RegisterDTO registerDTO) {
        return registerDAO.save(new Register(registerDTO.getRegisterId(),registerDTO.getAdvanced(),registerDTO.getDate(),registerDTO.getCourse(),registerDTO.getStudent()));
    }

    @Override
    public boolean update(RegisterDTO registerDTO) {
        return registerDAO.update(new Register(registerDTO.getRegisterId(),registerDTO.getAdvanced(),registerDTO.getDate(),registerDTO.getCourse(),registerDTO.getStudent()));
    }

    @Override
    public boolean delete(RegisterDTO registerDTO) {
        return registerDAO.delete(registerDTO.getRegisterId());
    }


    @Override
    public boolean search(String id) {
        return false;
    }

    @Override
    public String currentId() {
        return registerDAO.getCurrentId();
    }

    @Override
    public List<RegisterDTO> getAll() {
       List<RegisterDTO>registerDTOS = new ArrayList<>();
       List<Register> registers = registerDAO.getAll();
       for(Register register:registers){
           registerDTOS.add(new RegisterDTO(
                   register.getRegisterId(),
                   register.getAdvanced(),
                   register.getDate(),
                   register.getCourse(),
                   register.getStudent()
           ));
       }
       return registerDTOS;
    }

    @Override
    public List<Course> getProgramId() {
        return registerDAO.getProgramId();
    }

    @Override
    public List<Student> getStudentId() {
        return registerDAO.getStudentId();
    }

    @Override
    public List<String> getIds() {
        return registerDAO.getIds();
    }

    @Override
    public Register searchByRegisterId(String registerId) {
        return registerDAO.searchByRegisterId(registerId);
    }
}
