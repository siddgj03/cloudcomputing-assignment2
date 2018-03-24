package edu.neu.services;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.google.gson.Gson;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import edu.neu.cloucomputing.Course;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/course")
public class CourseServices {

    static AmazonDynamoDB user;
    static AmazonSNSClient snsUser;

    private static void snsInit(){
        ProfileCredentialsProvider credentialsProvider =
                new ProfileCredentialsProvider();
        credentialsProvider.getCredentials();

        snsUser = new AmazonSNSClient(credentialsProvider);
        snsUser.setRegion(Region.getRegion(Regions.US_WEST_2));
    }

    private static void init() throws Exception{
        ProfileCredentialsProvider credentialsProvider =
                new ProfileCredentialsProvider();
        credentialsProvider.getCredentials();

        user = AmazonDynamoDBClientBuilder
                .standard()
                .withCredentials(credentialsProvider)
                .withRegion("us-west-2")
                .build();
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCourse(@PathParam("name") String name) throws Exception {
        init();
        DynamoDB dynamoDB = new DynamoDB(user);
        Table table = dynamoDB.getTable("Course");
        Item item = table.getItem("name", name);
        if (item == null) {
            return null;
        }
        return item.toJSON();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public String createStudent(String json) throws Exception {
        init();
        DynamoDB dynamoDB = new DynamoDB(user);
        Table table = dynamoDB.getTable("Course");
        Gson gson = new Gson();
        Course course = gson.fromJson(json, Course.class);
        if (table.getItem("name", course.getCourseName()) != null) {
            return null;
        }
        Item item = new Item()
                .withPrimaryKey("name", course.getCourseName());
        PutItemOutcome outcome = table.putItem(item);
        snsInit();
        new CreateTopicRequest(course.getCourseName());
        return gson.toJson(outcome);
    }
}
