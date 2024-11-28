package org.example.bo.custom.impl;

import org.example.bo.custom.AdminBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.AdminDAO;
import org.example.dto.AdminDTO;
import org.example.entity.Admin;

import java.util.ArrayList;
import java.util.List;

public class AdminBOImpl implements AdminBO {
    AdminDAO adminDAO = (AdminDAO) DAOFactory.getDaoFactory().getDAOType(DAOFactory.DAOType.Admin);
    @Override
    public boolean save(AdminDTO adminDTO) {
        return adminDAO.save(new Admin(adminDTO.getUserId(),adminDTO.getUserName(),adminDTO.getTelephone(),adminDTO.getEmail(),adminDTO.getPassword()));
    }

    @Override
    public boolean update(AdminDTO adminDTO) {
        return adminDAO.update(new Admin(
                adminDTO.getUserId(),adminDTO.getUserName(),adminDTO.getTelephone(),adminDTO.getEmail(),adminDTO.getPassword()
        ));
    }

    @Override
    public String getCurrentId() {
        return adminDAO.getCurrentId();
    }

    @Override
    public List<AdminDTO> getAll() {
        List<Admin>all = adminDAO.getAll();
        List<AdminDTO> adminDTOS = new ArrayList<>();
        if (all == null){
            return null;
        }else {
            for (Admin admin : all){
                adminDTOS.add(new AdminDTO(admin.getUserId(),admin.getUserName(),admin.getTelephone(),admin.getEmail(),admin.getPassword()));
            }
        }
        return adminDTOS;
    }

    @Override
    public Admin searchByEmail(String email) {
        return adminDAO.searchByEmail(email);
    }
}
