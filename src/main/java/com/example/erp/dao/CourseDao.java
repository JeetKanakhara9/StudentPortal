package com.example.erp.dao;

import com.example.erp.bean.Courses;
import com.example.erp.bean.Students;

import java.util.List;

public interface CourseDao {

    public boolean registerCourse(List<Integer> id);
    public List<Courses> getCourses(Students student);
    public Courses getCourseByID(Integer id);
}