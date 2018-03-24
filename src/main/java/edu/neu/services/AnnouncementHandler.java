package edu.neu.services;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;

public class AnnouncementHandler {
    private AmazonSNS SNS_Client = AmazonSNSClientBuilder.standard()
            .withRegion(Regions.US_WEST_2).build();
    private static String COURSE_SNS_TOPIC = "arn:aws:sns:us-west-2:243824163312:StudentNotification";

    //@Override
    public String handleRequest(DynamodbEvent input, Context context) {
        // TODO Auto-generated method stub
        for(DynamodbEvent.DynamodbStreamRecord record : input.getRecords()) {
            if (record == null) {
                continue;
            }
            context.getLogger().log("Input: " + record);
            String courseName = " ";
            String content = " ";
            try {
                courseName = getCourseName(record);
                content = getCourseContent(record);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            context.getLogger().log("Course name :" + courseName);
            context.getLogger().log("Course Content:" + content);
            sendEmailNotification(courseName, content);
        }
        return "";
    }

    private void sendEmailNotification(String courseName, String content) {
        PublishRequest publishRequest = new PublishRequest(COURSE_SNS_TOPIC + courseName, content, courseName);
        SNS_Client.publish(publishRequest);
    }

    private String getCourseName(DynamodbEvent.DynamodbStreamRecord record) throws Exception {
        String courseName = "";
        courseName = record.getDynamodb().getNewImage().get("course").getS();
        return courseName;
    }

    private String getCourseContent(DynamodbEvent.DynamodbStreamRecord record) throws Exception {
        String content = "";
        content = record.getDynamodb().getNewImage().get("content").getS();
        return content;
    }
}
