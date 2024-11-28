package org.example.bo.custom;

import org.example.bo.SuperBo;
import org.example.dto.AdminDTO;
import org.example.entity.Admin;

import java.util.List;

public interface AdminBO extends SuperBo {
    boolean save(AdminDTO adminDTO);
    boolean update(AdminDTO adminDTO);
    String getCurrentId();
    List<AdminDTO> getAll();
    Admin searchByEmail(String email);

}
