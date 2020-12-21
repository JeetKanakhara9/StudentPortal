package com.example.erp.dao;

import com.example.erp.bean.Courses;
import com.example.erp.bean.Students;

import java.util.List;

public interface CourseDao {

    public boolean registerCourse(Courses course);
    public List<Courses> getCourses(Students student);
    public Courses getCourseByID(Integer id);
}