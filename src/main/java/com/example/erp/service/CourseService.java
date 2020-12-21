package com.example.erp.service;

import com.example.erp.bean.Courses;
import com.example.erp.bean.Students;
import com.example.erp.dao.CourseDao;
import com.example.erp.dao.implementation.CourseDaoImpl;

import java.util.List;

public class CourseService {

    CourseDao courseDao = new CourseDaoImpl();
    public boolean registerCourse(Courses course){
        return courseDao.registerCourse(course);
    }

    public List<Courses> getCourses (Students student){
        return courseDao.getCourses(student);
    }

    public Courses getCourseByID(Integer id){
        return courseDao.getCourseByID(id);
    }
}