package com.example.erp.dao.implementation;

import com.example.erp.bean.Courses;
import com.example.erp.bean.Students;
import com.example.erp.dao.CourseDao;
import com.example.erp.utils.SessionUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CourseDaoImpl implements CourseDao {
    @Override
    public boolean registerCourse(Courses course) {
        try (Session session = SessionUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(course);
            transaction.commit();
            return true;
        } catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public List<Courses> getCourses(Students student) {
        Session session = SessionUtil.getSession();
        List<Courses> courses = new ArrayList<>();
        try{

        Query query = session.createQuery("from Students where student_id=:id");
        query.setParameter("id", student.getStudent_id());
        Students s= (Students) query.uniqueResult();
                List<Courses> allcourses=s.getCourses();
                for(Courses c :allcourses) {
                    List<Courses> eligible_course =c.getPre_course();
                    //System.out.println(eligible_course.size());
                    for(Courses e:eligible_course) {
                        List<Courses> allpre=e.getPrereq();
                        int f=0;
                        for(Courses all:allpre) {
                            //System.out.println(all.getDescription());

                            for(Courses ch:allcourses)
                            {
                                if(all.getCourse_id()==ch.getCourse_id())
                                    f++;
                            }

                        }
                        // System.out.println(f);
                        //System.out.println(allpre.size());
                        if(f==allpre.size())
                        {
                           // System.out.println(e.getDescription());
                            courses.add(e);
                        }

                    }

                }
            }

           // ((Students) fetch).getCourses();
        //Students s=
       // try {
//            for (final Object fetch : query.list())
//                Students s=(Students) fetch;
           // List<Courses> allcourses=s.getCourses();
//            for(Courses c :allcourses) {
//                List<Courses> eligible_course =c.getPrereq();
//                //System.out.println(eligible_course.size());
//                for(Courses e:eligible_course) {
//                    List<Courses> allpre=e.getPre_course();
//                    int f=0;
//                    for(Courses all:allpre) {
//                        //System.out.println(all.getDescription());
//
//                        for(Courses ch:allcourses)
//                        {
//                            if(all.getCourse_id()==ch.getCourse_id())
//                                f++;
//                        }
//
//                    }
//                   // System.out.println(f);
//                    //System.out.println(allpre.size());
//                    if(f==allpre.size())
//                    {
//                        System.out.println(e.getDescription());
//                        courses.add(e);
//                    }
//
//                }
//
//            }
           // for (final Object course : session.createQuery("from Courses ").list()) {
                //courses.add((Courses) course);
           // }
         catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
        }
        //for(Courses c:courses)
          //  System.out.println(c.getDescription());
        return courses;
    }

    @Override
    public Courses getCourseByID(Integer id) {
        try (Session session = SessionUtil.getSession()) {
            return session.get(Courses.class, id);
        } catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
        }
        return null;
    }
}