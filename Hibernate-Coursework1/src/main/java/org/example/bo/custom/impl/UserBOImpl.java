package org.example.bo.custom.impl;

import org.example.bo.custom.UserBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.UserDAO;
import org.example.dto.UserDTO;
import org.example.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAOType(DAOFactory.DAOType.User);

    @Override
    public boolean save(UserDTO userDTO) {
        return userDAO.save(new User(userDTO.getUserId(),userDTO.getUserName(),userDTO.getTelephone(),
                userDTO.getEmail(),userDTO.getPassword()));
    }

    @Override
    public boolean update(UserDTO userDTO) {
        return userDAO.update(new User(
                userDTO.getUserId(),userDTO.getUserName(),userDTO.getTelephone(),userDTO.getEmail(),userDTO.getPassword()
        ));
    }

    @Override
    public boolean delete(String id) {
        return userDAO.delete(id);
    }

    @Override
    public User search(String id) {
        return userDAO.search(id);
    }

    @Override
    public String getCurrentId() {
        return userDAO.getCurrentId();
    }

    @Override
    public List<UserDTO> getAll() {
        List<User> all = userDAO.getAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        if (all == null) {
            return null;
        }else {
            for (User user : all) {
                userDTOList.add(new UserDTO(user.getUserId(),user.getUserName(),user.getTelephone(),user.getEmail(),user.getPassword()));
            }
        }
        return userDTOList;
    }

    @Override
    public User searchByEmail(String email) {
        return userDAO.searchByEmail(email);
    }
}
