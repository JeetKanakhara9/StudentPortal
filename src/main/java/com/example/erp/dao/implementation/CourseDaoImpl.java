package com.example.erp.dao.implementation;

import com.example.erp.bean.Courses;
import com.example.erp.bean.Students;
import com.example.erp.dao.CourseDao;
import com.example.erp.utils.SessionUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.*;

public class CourseDaoImpl implements CourseDao {
    @Override
    public boolean registerCourse(List<Integer> id) {
        try (Session session = SessionUtil.getSession()) {
            Query query = session.createQuery("from Students where student_id=:id1");
            query.setParameter("id1",id.get(0));
            //Students s=(Students) query.getResultList();
            List l =query.list();
            Iterator it = l.iterator();
            List<Courses> s_c=new ArrayList<Courses>();
            while(it.hasNext()) {
                Object o = (Object) it.next();
                Students s = (Students) o;
                // System.out.println("Product id : "+p.getProductId());
                //System.out.println("Product Name : "+p.getProName());
                //System.out.println("Product Price : "+p.getPrice());
                //System.out.println("----------------------");
                s_c = s.getCourses();

                //List<Courses> s_c=s.getCourses();
                for (int i = 1; i < id.size(); i++) {
                    System.out.println("ID in Dao");
                    System.out.println(id.get(i));
                    Query q1 = session.createQuery("from Courses where course_id=:cid");
                    q1.setParameter("cid", id.get(i));
                    //Courses c=(Courses) q1.getResultList();
                    List l1 = q1.list();
                    Iterator i1 = l1.iterator();
                    while (i1.hasNext()) {
                        Object o1 = (Object) i1.next();
                        Courses c = (Courses) o1;
                        int cap=c.getCapacity();
                        cap--;
                        c.setCapacity(cap);
                        s_c.add(c);
                        int credit=s.getCredits();
                        credit+=c.getCredits();
                        s.setCredits(credit);
                        Transaction transaction = session.beginTransaction();
                        session.saveOrUpdate(c);
                        transaction.commit();
                    }

                }
                s.setCourses(s_c);
                Transaction transaction = session.beginTransaction();
                session.saveOrUpdate(s);
                transaction.commit();
            }
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
            System.out.println("All Courses ");
            //for(Courses a1:allcourses)
               // System.out.println(a1.getDescription());
            for(Courses c :allcourses) {
                List<Courses> eligible_course =c.getPre_course();
                //System.out.println("Eligible courses for " + c.getDescription());
                for(Courses e : eligible_course)
                    System.out.println(e.getDescription());
                for(Courses e:eligible_course) {
                    List<Courses> allpre=e.getPrereq();
                   // System.out.println("Prereq courses for " + e.getDescription());
                    for(Courses p : allpre)
                      //  System.out.println(p.getDescription());

                        if(allcourses.containsAll(allpre)){
                            //System.out.println("Contains all pre");
                            if(!allcourses.contains(e)&&!courses.contains(e)) {
                               // System.out.println("allcourses does not contain " + e.getDescription());
                                courses.add(e);
                            }
                    }

                }

            }
            Query q2=session.createQuery("from Courses");
            List<Courses> c_all= q2.getResultList();
            System.out.println("Size of all courses in DB : "+ c_all.size());
            for(Courses c:c_all)
            {
//                System.out.println("Course  "+c.getDescription());
//                System.out.println("Prereq Course ");
                List<Courses> pid=c.getPrereq();
//                for(Courses p:pid)
//                    System.out.println(p.getCourse_id());
                if(pid.size()==1&&pid.get(0).getCourse_id()==-1)
                {
                    if(!courses.contains(c)&&!allcourses.contains(c))
                        courses.add(c);
                }
            }
        }

        catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
        }


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