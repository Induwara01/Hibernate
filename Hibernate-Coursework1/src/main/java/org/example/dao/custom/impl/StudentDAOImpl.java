package org.example.dao.custom.impl;

import org.example.config.FactoryConfiguration;
import org.example.dao.custom.StudentDAO;
import org.example.entity.Student;
import org.example.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public List<String> getIds() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("select studentId from Student");
        List<String> list = query.getResultList();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public String getCurrentId() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("select studentId from Student order by studentId desc limit 1");
        String id = (String) query.uniqueResult();
        transaction.commit();
        session.close();
        return id;
    }

    @Override
    public Student searchByStudentId(String studentId) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Student student = (Student) session.createQuery("from Student where studentId = :studentId").setParameter("studentId",studentId).uniqueResult();
        transaction.commit();
        session.close();
        return student;
    }

    @Override
    public Student getObject(String value) {
        return null;
    }

    @Override
    public boolean save(Student object) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(object);
        transaction.commit();
        return true;
    }

    @Override
    public boolean update(Student object) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(object);
        transaction.commit();
        return true;
    }

    @Override
    public boolean delete(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Student student = (Student) session.get(Student.class, id);
        session.delete(student);
        transaction.commit();
        return true;
    }

    @Override
    public Student search(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        NativeQuery<Student>nativeQuery = session.createNativeQuery("select * from student where studentId = :studentId", Student.class);
        nativeQuery.setParameter("studentId",id);

        List<Student> studentList = nativeQuery.list();
        transaction.commit();
        session.close();
        return studentList.isEmpty() ? null : studentList.get(0);
    }

    @Override
    public Student get(Student object) {
        return null;
    }

    @Override
    public List<Student> getAll() {
       Session session = FactoryConfiguration.getInstance().getSession();
       Transaction transaction = session.beginTransaction();
       NativeQuery nativeQuery = session.createNativeQuery("select * from Student");
       nativeQuery.addEntity(Student.class);
       List<Student> students = nativeQuery.getResultList();
       transaction.commit();
       session.close();
       return students;
    }
}
