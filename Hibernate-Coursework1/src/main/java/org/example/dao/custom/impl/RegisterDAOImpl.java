package org.example.dao.custom.impl;

import org.example.config.FactoryConfiguration;
import org.example.dao.custom.RegisterDAO;
import org.example.entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class RegisterDAOImpl implements RegisterDAO {
    @Override
    public List<Course> getProgramId() {
        List<Course>courses=new ArrayList<>();
        try {
            Session session = FactoryConfiguration.getInstance().getSession();

            String hql = "from Course";
            Query<Course>query = session.createQuery(hql, Course.class);
            courses = query.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return courses;
    }

    @Override
    public List<Student> getStudentId() {
        List<Student>students=new ArrayList<>();
        try {
            Session session = FactoryConfiguration.getInstance().getSession();
            String hql = "from Student";
            Query<Student>query = session.createQuery(hql, Student.class);
            students = query.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public String getCurrentId() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("select registerId from Register order by registerId desc limit 1");
        String id = (String) query.uniqueResult();
        transaction.commit();
        session.close();
        return id;
    }

    @Override
    public List<String> getIds() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("select registerId from Register");
        List<String>list = query.getResultList();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public Register searchByRegisterId(String registerId) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Register register = (Register) session.createQuery("from Register where registerId = :registerId").setParameter("registerId", registerId).uniqueResult();
        transaction.commit();
        session.close();
        return register;
    }

    @Override
    public boolean save(Register object) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(object);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Register object) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public Register search(String id) {
        return null;
    }
    @Override
    public Register get(Register object) {
        return null;
    }

    @Override
    public List<Register> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery<Register> nativeQuery = session.createNativeQuery("SELECT * FROM Register", Register.class);
        List<Register> registers = nativeQuery.list();
        transaction.commit();
        session.close();
        return registers;
    }
//    @Override
//    public String getCurrentId() {
//        Session session = FactoryConfiguration.getInstance().getSession();
//        Transaction transaction = session.beginTransaction();
//        Query query = session.createQuery("select registerId from Register order by registerId desc limit 1");
//        String id = (String) query.uniqueResult();
//        transaction.commit();
//        session.close();
//        return id;
//    }
//
//    @Override
//    public boolean save(Object object) {
//        Session session = FactoryConfiguration.getInstance().getSession();
//        Transaction transaction = session.beginTransaction();
//        session.save(object);
//        transaction.commit();
//        session.close();
//        return true;
//    }
//
//    @Override
//    public boolean update(Object object) {
//        Session session = FactoryConfiguration.getInstance().getSession();
//        Transaction transaction = session.beginTransaction();
//        session.update(object);
//        transaction.commit();
//        session.close();
//        return true;
//    }
//
//    @Override
//    public boolean delete(Object object) {
//        Session session = FactoryConfiguration.getInstance().getSession();
//        Transaction transaction = session.beginTransaction();
//        session.delete(object);
//        transaction.commit();
//        session.close();
//        return true;
//    }
//
//    @Override
//    public Object search(String id) {
//        return null;
//    }
//
//    @Override
//    public Object get(Object object) {
//        return null;
//    }
//
//    @Override
//    public List<Register>getAll() {
//        Session session = FactoryConfiguration.getInstance().getSession();
//        Transaction transaction = session.beginTransaction();
//        NativeQuery nativeQuery = session.createNativeQuery("select * from register");
//        List<Register>registers = nativeQuery.list();
//        transaction.commit();
//        session.close();
//        return registers;
//    }
}
