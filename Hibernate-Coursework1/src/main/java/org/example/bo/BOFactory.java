package org.example.bo;

import org.example.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {}
    public enum BOType {
        Course,User,Student,Admin,Register,Payment;
    }
    public static BOFactory getBOFactory() {
        return boFactory == null ? new BOFactory() : boFactory;
    }
    public SuperBo getBOType(BOType boType) {
        switch (boType) {
            case Course:
                return new CourseBOImpl();
            case User:
                return new UserBOImpl();
            case Student:
                return new StudentBOImpl();
            case Admin:
                return new AdminBOImpl();
            case Register:
                return new RegisterBOImpl();
            case Payment:
                return new PaymentBOImpl();
            default:
                return null;
        }
    }
}
