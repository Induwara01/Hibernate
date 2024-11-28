package org.example.dao;

import org.example.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){}

    public enum DAOType{
        Course,User,Student,Admin,Register,Payment;
    }

    public static DAOFactory getDaoFactory(){
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }

    public SuperDAO getDAOType(DAOType daoType){
        switch (daoType){
            case Course:
                return new CourseDAOImpl();
            case User:
                return new UserDAOImpl();
            case Student:
                return new StudentDAOImpl();
                case Admin:
                    return new AdminDAOImpl();
            case Register:
                return new RegisterDAOImpl();
            case Payment:
                return new PaymentDAOImpl();
                default:
                    return null;
        }
    }
}
