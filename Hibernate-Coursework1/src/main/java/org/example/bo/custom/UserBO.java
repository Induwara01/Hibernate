package org.example.bo.custom;

import org.example.bo.SuperBo;
import org.example.dto.UserDTO;
import org.example.entity.User;

import java.util.List;

public interface UserBO extends SuperBo{
    boolean save(UserDTO userDTO);
    boolean update(UserDTO userDTO);
    boolean delete(String id);
    User search(String id);
    String getCurrentId();
    List<UserDTO>getAll();
    User searchByEmail(String email);
}
