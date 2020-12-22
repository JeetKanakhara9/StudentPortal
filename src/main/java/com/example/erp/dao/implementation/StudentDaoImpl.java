package com.example.erp.dao.implementation;

import com.example.erp.bean.Courses;
import com.example.erp.bean.Students;
import com.example.erp.dao.StudentDao;
import com.example.erp.utils.SessionUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {

    @Override
    public Students emailVerify(Students student) {
        try (Session session = SessionUtil.getSession()) {
            Query query = session.createQuery("from Students where email=:email");
            query.setParameter("email", student.getEmail());

            for (final Object fetch : query.list()) {
                Students s=(Students) fetch;
                return (Students) fetch;
            }
        } catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return null;
        }
        return null;
    }

    @Override
    public Students getdata(Students student) {
        try (Session session = SessionUtil.getSession()) {
            Query query = session.createQuery("from Students where student_id=:id");
            query.setParameter("id", student.getStudent_id());

            for (final Object fetch : query.list()) {
                Students s=(Students) fetch;
                return (Students) fetch;
            }
        } catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return null;
        }

        return null;
    }

    @Override
    public List<Courses> getCourse(Students student) {
        try (Session session = SessionUtil.getSession()) {
            Query query = session.createQuery("from Students where student_id=:id");
            query.setParameter("id", student.getStudent_id());
            Students s= (Students) query.uniqueResult();
            List<Courses> courses=s.getCourses();
            return courses;
//            for (final Object fetch : query.list()) {
//                Students s=(Students) fetch;
//                List<Courses> courses=s.getCourses();
//                return courses;
//
//            }
        } catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return null;
        }


        //return null;
    }

    @Override
    public boolean registerStudent(Students student) {
        Session session = SessionUtil.getSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.save(student);
            transaction.commit();
            return true;
        } catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return false;
        }finally {
            session.close();
        }
    }
}
