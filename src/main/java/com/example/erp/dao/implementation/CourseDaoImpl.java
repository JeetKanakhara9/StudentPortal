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

        catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
        }
        //for(Courses c:courses)
        //  System.out.println(c.getDescription());
        //Set<Courses> c1=new HashSet<Courses>(courses);
        //c1.addAll(courses);

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