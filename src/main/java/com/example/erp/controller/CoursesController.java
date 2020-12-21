package com.example.erp.controller;

import com.example.erp.bean.Courses;
import com.example.erp.bean.Students;
import com.example.erp.service.CourseService;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.print.URIException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Path("courses")
public class CoursesController {

    @POST
    @Path("/get")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourses(Students student) {
      //  List<String> courses = new ArrayList<>();
//        courses.add("Software Systems");
//        courses.add("Enterprise Software Development");
//        courses.add("Algorithms");
//        courses.add("Machine Learning");
//        courses.add("Maths for Machine Learning");
//        courses.add("Network Communications");
//        courses.add("Discrete Mathematics");
            CourseService courseService =new CourseService();
            List<Courses> courses=courseService.getCourses(student);
            System.out.println("Controller :");
            for(Courses c: courses)
               System.out.println(c.getDescription());
        return Response.ok().entity(courses).build();
    }

    @POST
    @Path("/register_course")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response register_Course(Courses course) throws URISyntaxException {
        //System.out.println(course.getCourse_id());
        //System.out.println(course.getDescription());
        CourseService courseService =new CourseService();

       // if(courseService.registerCourse(course))


        //System.out.println(credits);
        return Response.ok().build();

    }

    @POST
    @Path("/register")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response registerCourse(@FormDataParam("name") String name,
                                   @FormDataParam("description") String description,
                                   @FormDataParam("credits") Integer credits) throws URISyntaxException {
        System.out.println(name);
        System.out.println(description);
        System.out.println(credits);
        return Response.ok().build();

    }
}
