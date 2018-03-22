package edu.neu.cloucomputing;

public class CourseRegistration {
    private Integer id;
    private String courseName;

    public CourseRegistration(String courseName, Integer id){
        this.courseName = courseName;
        this.id = id;
    }

    public String getCourseName(){
        return courseName;
    }

    public Integer getId(){
        return id;
    }
}
