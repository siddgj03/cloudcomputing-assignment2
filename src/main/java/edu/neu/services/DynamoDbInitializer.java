package edu.neu.services;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;

public class DynamoDbInitializer {
    static AmazonDynamoDB dynamoDb;

    private static void init() throws Exception {
        ProfileCredentialsProvider credentialsProvider =
                new ProfileCredentialsProvider();
        credentialsProvider.getCredentials();

        dynamoDb = AmazonDynamoDBClientBuilder
                .standard()
                .withCredentials(credentialsProvider)
                .withRegion("us-west-2")
                .build();
    }

    private static void createStudentTable() {
        String tableName = "Student";
        CreateTableRequest createTableRequest = new CreateTableRequest()
                .withTableName(tableName)
                .withKeySchema(
                        new KeySchemaElement()
                                .withAttributeName("id")
                                .withKeyType(KeyType.HASH))
                .withAttributeDefinitions(
                        new AttributeDefinition()
                                .withAttributeName("name")
                                .withAttributeType(ScalarAttributeType.N),
                        new AttributeDefinition()
                                .withAttributeName("email")
                                .withAttributeType(ScalarAttributeType.N))
                .withProvisionedThroughput(
                        new ProvisionedThroughput()
                                .withReadCapacityUnits(3L)
                                .withWriteCapacityUnits(3L));
        TableUtils.createTableIfNotExists(dynamoDb, createTableRequest);
        try {
            TableUtils.waitUntilActive(dynamoDb, tableName);
        } catch (TableUtils.TableNeverTransitionedToStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void createAnnouncementTable() {
        String tableName = "Announcement";
        CreateTableRequest createTableRequest = new CreateTableRequest()
                .withTableName(tableName)
                .withKeySchema(
                        new KeySchemaElement()
                                .withAttributeName("id")
                                .withKeyType(KeyType.HASH))
                .withAttributeDefinitions(
                        new AttributeDefinition()
                                .withAttributeName("professorId")
                                .withAttributeType(ScalarAttributeType.N),
                        new AttributeDefinition()
                                .withAttributeName("Message")
                                .withAttributeType(ScalarAttributeType.N))
                .withProvisionedThroughput(
                        new ProvisionedThroughput()
                                .withReadCapacityUnits(3L)
                                .withWriteCapacityUnits(3L));
        TableUtils.createTableIfNotExists(dynamoDb, createTableRequest);
        try {
            TableUtils.waitUntilActive(dynamoDb, tableName);
        } catch (TableUtils.TableNeverTransitionedToStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void createProfessorTable() {
        String tableName = "Profesor";
        CreateTableRequest createTableRequest = new CreateTableRequest()
                .withTableName(tableName)
                .withKeySchema(
                        new KeySchemaElement()
                                .withAttributeName("id")
                                .withKeyType(KeyType.HASH))
                .withAttributeDefinitions(
                        new AttributeDefinition()
                                .withAttributeName("name")
                                .withAttributeType(ScalarAttributeType.N))
                .withProvisionedThroughput(
                        new ProvisionedThroughput()
                                .withReadCapacityUnits(3L)
                                .withWriteCapacityUnits(3L));
        TableUtils.createTableIfNotExists(dynamoDb, createTableRequest);
        try {
            TableUtils.waitUntilActive(dynamoDb, tableName);
        } catch (TableUtils.TableNeverTransitionedToStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       /* Map<String, AttributeValue> item =
                new HashMap<String, AttributeValue>();

        item.put("chipotle", new AttributeValue().withS("500-john-street"));

        PutItemRequest putItemRequest = new PutItemRequest(tableName, item);
        System.out.println("PutItemRequest :" +putItemRequest);*/
        //PutItemRequest putItemResult = dynamoDb.putItem(putItemRequest);
        //System.out.println("PutItemRequest :" +putItemResult);
    }

    private static void createCourseTable() {
        String tableName = "Course";
        CreateTableRequest createTableRequest = new CreateTableRequest()
                .withTableName(tableName)
                .withKeySchema(
                        new KeySchemaElement()
                                .withAttributeName("name")
                                .withKeyType(KeyType.HASH))
                .withAttributeDefinitions(
                        new AttributeDefinition()
                                .withAttributeName("name")
                                .withAttributeType(ScalarAttributeType.N),
                        new AttributeDefinition()
                                .withAttributeName("name")
                                .withAttributeType(ScalarAttributeType.N))
                .withProvisionedThroughput(
                        new ProvisionedThroughput()
                                .withReadCapacityUnits(3L)
                                .withWriteCapacityUnits(3L));
        TableUtils.createTableIfNotExists(dynamoDb, createTableRequest);
        try {
            TableUtils.waitUntilActive(dynamoDb, tableName);
        } catch (TableUtils.TableNeverTransitionedToStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        DynamoDbInitializer dbInit = new DynamoDbInitializer();
        init();
        createStudentTable();
        createAnnouncementTable();
        createCourseTable();
        createProfessorTable();

    }
}
