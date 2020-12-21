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
                //System.out.print(f1.getStudent_id());
                //System.out.print(f1.getFirst_name());
                List<Courses> courses=new ArrayList<Courses>();

                List<Courses> allcourses=s.getCourses();
                for(Courses c :allcourses) {
                    List<Courses> eligible_course =c.getPrereq();
                    //System.out.println(eligible_course.size());
                    for(Courses e:eligible_course) {
                        List<Courses> allpre=e.getPre_course();
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
               // for(Courses c: courses)
                 //   System.out.println(c.getDescription());
                return (Students) fetch;
            }
        } catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return null;
        }
        return null;
    }

   /* @Override
    public Students emailVerify(Students student) {
        Session session = SessionUtil.getSession();
        try {

            Query query = session.createQuery("select student_id from Students where email=:email");
            query.setParameter("email", student.getEmail());
            List<Integer> id = query.getResultList();
            if (id.size() == 1) {
                //System.out.println(student.getCourses());
                Query q1 = session.createQuery("from Students where student_id=:i");
                q1.setParameter("i", id.get(0));
                Students s = (Students)q1.uniqueResult();
               /*for (Courses c:s.getCourses()) {
                   System.out.println(c.getCourse_id());
                   System.out.println(c.getCourse_code());aksh
                   System.out.println(c.getDescription());
               }*/
              /* List<Courses> allcourses=s.getCourses();
               for(Courses c :allcourses) {
                   List<Courses> eligible_course =c.getPrereq();
                   //System.out.println(eligible_course.size());
                   for(Courses e:eligible_course) {
                       List<Courses> allpre=e.getPre_course();
                       int f=0;
                       for(Courses all:allpre) {
                           //System.out.println(all.getDescription());

                           for(Courses ch:allcourses)
                           {
                               if(all.getCourse_id()==ch.getCourse_id())
                                   f++;
                           }

                       }
                       System.out.println(f);
                       System.out.println(allpre.size());
                       if(f==allpre.size())
                       {
                           System.out.println(e.getDescription());
                       }

                   }

               }*/


                //return s;
           // }



//        catch (HibernateException exception) {
//            System.out.print(exception.getLocalizedMessage());
//
//            return null;
//        }finally {
//            session.close();
//        }
//        return null;
//    }

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
