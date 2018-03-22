package edu.neu.cloucomputing;

public class Professor {
    private Integer professorID;
    private String professorName;
    private String courseName;

    public Professor(Integer professorID, String professorName, String courseName){
        this.professorID = professorID;
        this.professorName = professorName;
        this.courseName = courseName;
    }
    public Integer getProfessorID() {
        return professorID;
    }
    public String getCourseName() {
        return courseName;
    }
    public String getProfessorName() {
        return professorName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setProfessorID(Integer professorID) {
        this.professorID = professorID;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }
}
