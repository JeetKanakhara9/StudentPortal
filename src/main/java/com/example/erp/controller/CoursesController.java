package com.example.erp.controller;

import com.example.erp.bean.Courses;
import com.example.erp.bean.Students;
import com.example.erp.service.CourseService;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
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
    public Response register_Course(JsonObject course_id) throws URISyntaxException {
        //System.out.println(course.getCourse_id());
        //System.out.println(course.getDescription());
        //System.out.println(course_id.getString("student_id"));
        //System.out.println(course_id.getString("cid"));
        String s=course_id.getString("cid");
        //System.out.println(s);
        //JsonArray a= (JsonArray) course_id.get("cid");
        //System.out.println(a);
        List<Integer> id=new ArrayList<Integer>();
        id.add( Integer.parseInt(course_id.getString("student_id")));
        //id.add(course_id.getInt("cid",0));
        //id.add(course_id.getInt("cid",1));

        for(int i=0;i<s.length();i++) {
            //System.out.println(s.charAt(i));
            if(Character.isDigit(s.charAt(i)))
                id.add(Character.getNumericValue(s.charAt(i)));
                //System.out.println(s.charAt(i));
        }
//        for(int i :id)
//            System.out.println(i);


        CourseService courseService =new CourseService();

       if(courseService.registerCourse(id))
           return Response.ok().build();


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
