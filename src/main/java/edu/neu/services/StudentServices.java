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
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.google.gson.Gson;
import edu.neu.cloucomputing.CourseRegistration;
import edu.neu.cloucomputing.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

public class StudentServices {
    static AmazonDynamoDB user;
    static String arn = "";
    static AmazonSNSClient snsUser;

    private static void snsInit() {
        ProfileCredentialsProvider credentialsProvider =
                new ProfileCredentialsProvider();
        credentialsProvider.getCredentials();

        snsUser = new AmazonSNSClient(credentialsProvider);
        snsUser.setRegion(Region.getRegion(Regions.US_WEST_2));
    }

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

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getStudent(@PathParam("id") long id) throws Exception {
        init();
        DynamoDB dynamoDB = new DynamoDB(user);
        Table table = dynamoDB.getTable("Student");
        Item item = table.getItem("id", id);
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
        Table table = dynamoDB.getTable("Student");
        Gson gson = new Gson();
        Student student = gson.fromJson(json, Student.class);
        if (table.getItem("id", student.getStudentID()) != null) {
            return null;
        }
        Item item = new Item()
                .withPrimaryKey("id", student.getStudentID())
                .withString("name", student.getStudentName())
                .withString("email", student.getEmail());
        PutItemOutcome outcome = table.putItem(item);
        return gson.toJson(outcome);
    }

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public String register(String json) throws Exception {
        init();
        snsInit();
        DynamoDB dynamoDB = new DynamoDB(user);
        Table studentTable = dynamoDB.getTable("Student");
        Table courseTable = dynamoDB.getTable("Course");
        Gson gson = new Gson();
        CourseRegistration registration = gson.fromJson(json, CourseRegistration.class);
        Item studentItem = studentTable.getItem("id", registration.getId());
        Item courseItem = courseTable.getItem("name", registration.getCourseName());
        if (studentItem == null || courseItem == null) {
            return null;
        }

        SubscribeRequest subscribeRequest = new SubscribeRequest(arn + courseItem.getString("name"), "email", studentItem.getString("email"));
        snsUser.subscribe(subscribeRequest);
        return gson.toJson(registration);
    }


}
