package org.example.dao.custom.impl;

import org.example.config.FactoryConfiguration;
import org.example.dao.custom.AdminDAO;
import org.example.entity.Admin;
import org.example.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class AdminDAOImpl implements AdminDAO {
    @Override
    public boolean save(Admin object) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            String hashedPassword = BCrypt.hashpw(object.getPassword(), BCrypt.gensalt());
            object.setPassword(hashedPassword);

            session.save(object);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Admin object) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(object);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public Admin search(String id) {
        return null;
    }

    @Override
    public Admin get(Admin object) {
        return null;
    }

    @Override
    public List<Admin> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List<Admin> admins = session.createQuery("from Admin").list();
        transaction.commit();
        session.close();
        return admins;
    }

    @Override
    public String getCurrentId() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("select userId from Admin order by userId desc limit 1");
        String userId = (String) query.uniqueResult();
        transaction.commit();
        session.close();
        return userId;
    }

    @Override
    public Admin searchByEmail(String email) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Admin admin = (Admin) session.createQuery("from Admin where email = :email").setParameter("email", email).uniqueResult();
        transaction.commit();
        session.close();
        return admin;
    }
}
