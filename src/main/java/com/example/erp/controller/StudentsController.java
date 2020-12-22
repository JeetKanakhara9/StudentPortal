package com.example.erp.controller;

import com.example.erp.bean.Courses;
import com.example.erp.bean.Students;
import com.example.erp.service.StudentService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.List;

@Path("students")
public class StudentsController {
    StudentService studentService = new StudentService();
    @POST
    @Path("/register")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerStudent(Students student) throws URISyntaxException {
       // System.out.println(course.getDescription());
        if(studentService.registerStudent(student)){
            return Response.ok().build();
       }else{
            return Response.status(203).build();
        }
  }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginStudent(Students student) throws URISyntaxException {
        Students result = studentService.verifyEmail(student);
        System.out.println(result.getStudent_id());
        
        if(result == null){
            return Response.noContent().build();
        }

        return Response.ok().entity(result).build();
    }

    @POST
    @Path("/get_data")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getStudent_Data(Students student) throws URISyntaxException {
        Students result = studentService.getdata(student);
        System.out.println(result.getStudent_id());

        if(result == null){
            return Response.noContent().build();
        }

        return Response.ok().entity(result).build();
    }

    @POST
    @Path("/get_courses")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getCourses(Students student) throws URISyntaxException {
        List<Courses> course = studentService.getCourse(student);
       // System.out.println(result.getStudent_id());

        if(course == null){
            return Response.noContent().build();
        }

        return Response.ok().entity(course).build();
    }


}

