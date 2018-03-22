package edu.neu.cloucomputing;

public class Course {
    private String courseName;

    Course(String courseName){
        super();
        this.courseName = courseName;
    }

    public void setCourseName(String courseName){
        this.courseName = courseName;
    }

    public String getCourseName(){
        return courseName;
    }

}
