package edu.neu.cloucomputing;

public class Student {
    private String studentName;
    private Integer studentID;
    private String email;

    Student(Integer studentID, String studentName, String email){
        this.studentID = studentID;
        this.studentName = studentName;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getStudentID() {
        return studentID;
    }
}
