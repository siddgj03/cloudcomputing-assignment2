package edu.neu.services;


import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.google.gson.Gson;
import edu.neu.cloucomputing.Announcement;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/professor")
public class ProfessorServices {
    static AmazonDynamoDB user;

    private static void init() throws Exception {
        ProfileCredentialsProvider credentialsProvider =
                new ProfileCredentialsProvider();
        credentialsProvider.getCredentials();

        user = AmazonDynamoDBClientBuilder
                .standard()
                .withCredentials(credentialsProvider)
                .withRegion("us-west-2")
                .build();

    }

    @POST
    @Path("/announcement")
    public String makeAnnouncement(String json)throws Exception{
        init();
        DynamoDB dynamoDB = new DynamoDB(user);
        Table announcementTable = dynamoDB.getTable("Announcement");
        Table professorTable = dynamoDB.getTable("Professor");
        Table courseTable = dynamoDB.getTable("Course");
        Gson gson = new Gson();
        Announcement newAnnouncement = gson.fromJson(json, Announcement.class);
        Item profId = professorTable.getItem("id", newAnnouncement.getId());
        Item courseDetails = courseTable.getItem("name",profId.get("course"));
        if(profId == null || courseDetails == null){
            return null;
        }
        Item announcementItem = new Item().withPrimaryKey("id",newAnnouncement.getId())
                .withLong("profesorId",newAnnouncement.getProfId())
                .withString("Message",newAnnouncement.getMessage())
                .withString("course",courseDetails.getString("Name"));
        PutItemOutcome outcome = announcementTable.putItem(announcementItem);
        return gson.toJson(outcome);
    }
}
