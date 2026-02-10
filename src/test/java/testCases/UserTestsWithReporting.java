package testCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.github.javafaker.Faker;
import endpoints.UserEndpoints;
import io.restassured.response.Response;
import payload.User;
import utilities.ExtentReportManager;

/**
 * UserTestsWithReporting.java
 * Enhanced test class with ExtentReports and Log4j integration
 */
public class UserTestsWithReporting {
    
    Faker faker;
    User userPayload;
    public Logger logger;
    public static ExtentReports extent;
    public static ExtentTest test;
    
    @BeforeClass
    public void setupData() {
        // Initialize Faker
        faker = new Faker();
        
        // Create user payload
        userPayload = new User();
        userPayload.setName(faker.name().fullName());
        userPayload.setJob(faker.job().position());
        userPayload.setEmail(faker.internet().emailAddress());
        
        // Initialize logger
        logger = LogManager.getLogger(this.getClass());
        
        // Setup ExtentReports
        extent = ExtentReportManager.setupExtentReport();
        
        logger.info("========== Test Execution Started ==========");
    }
    
    @Test(priority = 1)
    public void testCreateUser() {
        test = extent.createTest("Create User Test");
        logger.info("========== Creating User ==========");
        
        test.info("Sending POST request to create user");
        Response response = UserEndpoints.createUser(userPayload);
        response.then().log().all();
        
        // Validations
        Assert.assertEquals(response.getStatusCode(), 201);
        test.pass("Status code validation passed: 201");
        logger.info("User created successfully with status code: " + response.getStatusCode());
        
        Assert.assertNotNull(response.jsonPath().getString("id"));
        test.pass("User ID is not null");
        
        Assert.assertNotNull(response.jsonPath().getString("createdAt"));
        test.pass("Created timestamp is not null");
        
        logger.info("========== User Created Successfully ==========");
    }
    
    @Test(priority = 2)
    public void testGetUser() {
        test = extent.createTest("Get User Test");
        logger.info("========== Fetching User Details ==========");
        
        test.info("Sending GET request to fetch user with ID: 2");
        Response response = UserEndpoints.getUser(2);
        response.then().log().all();
        
        // Validations
        Assert.assertEquals(response.getStatusCode(), 200);
        test.pass("Status code validation passed: 200");
        logger.info("User fetched successfully with status code: " + response.getStatusCode());
        
        Assert.assertNotNull(response.jsonPath().getString("id"));
        test.pass("User ID is present in response");
        
        Assert.assertNotNull(response.jsonPath().getString("email"));
        test.pass("Email is present in response");
        
        logger.info("========== User Fetched Successfully ==========");
    }
    
    @Test(priority = 3)
    public void testUpdateUser() {
        test = extent.createTest("Update User Test");
        logger.info("========== Updating User ==========");
        
        // Update payload
        userPayload.setName(faker.name().fullName());
        userPayload.setJob(faker.job().position());
        
        test.info("Sending PUT request to update user with ID: 2");
        Response response = UserEndpoints.updateUser(2, userPayload);
        response.then().log().all();
        
        // Validations
        Assert.assertEquals(response.getStatusCode(), 200);
        test.pass("Status code validation passed: 200");
        logger.info("User updated successfully with status code: " + response.getStatusCode());
        
        Assert.assertNotNull(response.jsonPath().getString("updatedAt"));
        test.pass("Updated timestamp is not null");
        
        logger.info("========== User Updated Successfully ==========");
    }
    
    @Test(priority = 4)
    public void testDeleteUser() {
        test = extent.createTest("Delete User Test");
        logger.info("========== Deleting User ==========");
        
        test.info("Sending DELETE request to delete user with ID: 2");
        Response response = UserEndpoints.deleteUser(2);
        response.then().log().all();
        
        // Validations - JSONPlaceholder returns 200 instead of 204
        Assert.assertEquals(response.getStatusCode(), 200);
        test.pass("Status code validation passed: 200");
        logger.info("User deleted successfully with status code: " + response.getStatusCode());
        
        logger.info("========== User Deleted Successfully ==========");
    }
    
    @Test(priority = 5)
    public void testGetUserList() {
        test = extent.createTest("Get User List Test");
        logger.info("========== Fetching User List ==========");
        
        test.info("Sending GET request to fetch user list for page: 2");
        Response response = UserEndpoints.getUserList(2);
        response.then().log().all();
        
        // Validations
        Assert.assertEquals(response.getStatusCode(), 200);
        test.pass("Status code validation passed: 200");
        logger.info("User list fetched successfully with status code: " + response.getStatusCode());
        
        Assert.assertTrue(response.jsonPath().getList("$").size() > 0);
        test.pass("User list contains data");
        
        logger.info("========== User List Fetched Successfully ==========");
    }
    
    @Test(priority = 6)
    public void testGetUserNotFound() {
        test = extent.createTest("Get User Not Found Test (Negative)");
        logger.info("========== Testing User Not Found Scenario ==========");
        
        test.info("Sending GET request for non-existent user ID: 999");
        Response response = UserEndpoints.getUser(999);
        response.then().log().all();
        
        // Validations
        Assert.assertEquals(response.getStatusCode(), 404);
        test.pass("Status code validation passed: 404 (Not Found)");
        logger.info("Negative test passed - User not found as expected");
        
        logger.info("========== Negative Test Completed ==========");
    }
    
    @AfterClass
    public void tearDown() {
        logger.info("========== Test Execution Completed ==========");
        extent.flush();
    }
}
