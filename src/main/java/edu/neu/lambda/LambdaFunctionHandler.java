package edu.neu.lambda;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;

/*public class LambdaFunctionHandler implements RequestHandler<DynamodbEvent, String> {

    private AmazonSNS SNS_CLIENT = AmazonSNSClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
    private static String RESTRAUNTS_SNS_TOPIC = "arn:aws:sns:us-west-2:243824163312:Restraunt";

    //@Override
    *//*public String handleRequest1(DynamodbEvent input, Context context){
        String inputString = String.valueOf(input);
        context.getLogger().log("input :" +input);
        String output = "Hello" + inputString + "|";
        String outputBody = output + "A new res opened";
        sendEmailNotification(output,outputBody);
        return output;
    }*//*

    @Override
    public String handleRequest(DynamodbEvent input, Context context){
        // Read DDB Records
        String output = " ";
        for(DynamodbStreamRecord record : input.getRecords()){

            if(record == null){
                continue;
            }
            record.getDynamodb();
            String inputString = record.toString();
            context.getLogger().log("input : " +input);
            output = "Hello, " + inputString + "!";
            String outputBody = output + "A new res. opened in your area!";
            //System.out.println(outputBody);
            //record.getDynamodb().getKeys()
            //your code here
            //getRestraunt from the record
            // getLocation from the record
            //Check for location to be Seattle
            //Send notification
            sendEmailNotification(output,outputBody);
        }
        return output;
    }

    private void sendEmailNotification(final String subject, final String message){
        // Message object
        PublishRequest publishRequest = new PublishRequest(RESTRAUNTS_SNS_TOPIC, message);
        // Call client.publishMessage
        SNS_CLIENT.publish(publishRequest);
    }

    private String getResturantName(String record){
        String resturantName = "abc";
        // JSON Parser logic
        return resturantName;
    }

    private String getResturantLocation(String record){
        String resturantLocation = "xyz";
        // JSON Parser logic
        return resturantLocation;
    }
}*/
