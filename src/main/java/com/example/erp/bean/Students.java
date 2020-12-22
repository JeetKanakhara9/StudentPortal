package com.example.erp.bean;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Students")
public class Students implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer student_id;
    @Column(nullable = false,unique=true)
    private String student_rollno;
    private String first_name;
    private String last_name;
    @Column(nullable = false, unique = true)
    private String email;
    private int credits;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Student_Courses", joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")})
    private List<Courses> courses;

    public Students() {
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public Students(Integer student_id, String student_rollno, String  first_name, String last_name, String email,int credits)
    {
        this.student_id=student_id;
        this.student_rollno=student_rollno;
        this.first_name=first_name;
        this.last_name=last_name;
        this.email=email;
        this.credits=credits;
    }
    public String getStudent_rollno() {
        return student_rollno;
    }

    public void setStudent_rollno(String student_rollno) {
        this.student_rollno = student_rollno;
    }
    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    @JsonbTransient
    public List<Courses> getCourses() {
        return courses;
    }

    public void setCourses(List<Courses> courses) {
        this.courses = courses;
    }
}
