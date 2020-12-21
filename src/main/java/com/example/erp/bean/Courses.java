package com.example.erp.bean;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Courses implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer course_id;
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private int credits;
    @Column(nullable = false, unique = true)
    private String course_code;
    @Column(nullable = false)
    private int capacity;

    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name="Course_Prereq",
            joinColumns={@JoinColumn(name="course_id")},
            inverseJoinColumns={@JoinColumn(name="pre_id")})
    private List<Courses> Prereq = new ArrayList<Courses>();

    @ManyToMany(mappedBy="Prereq")
    private List<Courses> pre_course = new ArrayList<Courses>();

    @JsonbTransient
    public List<Courses> getPrereq() {
        return Prereq;
    }

    public void setPrereq(List<Courses> prereq) {
        Prereq = prereq;
    }
    @JsonbTransient
    public List<Courses> getPre_course() {
        return pre_course;
    }

    public void setPre_course(List<Courses> pre_course) {
        this.pre_course = pre_course;
    }

    public Courses()
    {

    }
    public Courses(Integer course_id, String course_code, int capacity, List<Students> students) {
        this.course_id = course_id;
        this.course_code = course_code;
        this.capacity = capacity;
        this.students = students;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }



    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "courses")
    private List<Students> students;





    public Integer getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Integer course_id) {
        this.course_id = course_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    @JsonbTransient
    public List<Students> getStudents() {
        return students;
    }

    public void setStudents(List<Students> students) {
        this.students = students;
    }
}
