package edu.neu.cloucomputing;

public class Announcement {
    Integer id;
    Integer proffesorId;
    String message;

    public Announcement(Integer id, Integer profId, String content) {
        super();
        this.id = id;
        this.proffesorId = profId;
        this.message = content;
    }
    public long getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public long getProfId() {
        return proffesorId;
    }
    public void setProfId(Integer profId) {
        this.proffesorId = profId;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

}
