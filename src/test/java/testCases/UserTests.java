package testCases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import endpoints.UserEndpoints;
import io.restassured.response.Response;
import payload.User;

/**
 * UserTests.java
 * This class contains all test cases for User API
 */
public class UserTests {
    
    Faker faker;
    User userPayload;
    
    @BeforeClass
    public void setupData() {
        // Initialize Faker to generate random test data
        faker = new Faker();
        
        // Create user payload with random data
        userPayload = new User();
        userPayload.setName(faker.name().fullName());
        userPayload.setJob(faker.job().position());
        userPayload.setEmail(faker.internet().emailAddress());
    }
    
    @Test(priority = 1)
    public void testCreateUser() {
        // Send POST request to create user
        Response response = UserEndpoints.createUser(userPayload);
        
        // Print response
        response.then().log().all();
        
        // Validations
        Assert.assertEquals(response.getStatusCode(), 201, "Status code should be 201");
        Assert.assertNotNull(response.jsonPath().getString("id"), "User ID should not be null");
        Assert.assertNotNull(response.jsonPath().getString("createdAt"), "Created date should not be null");
        
        System.out.println("✅ User created successfully!");
    }
    
    @Test(priority = 2)
    public void testGetUser() {
        // Send GET request to fetch user
        Response response = UserEndpoints.getUser(2);
        
        // Print response
        response.then().log().all();
        
        // Validations
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        Assert.assertNotNull(response.jsonPath().getString("data.id"), "User ID should not be null");
        Assert.assertNotNull(response.jsonPath().getString("data.email"), "Email should not be null");
        
        System.out.println("✅ User fetched successfully!");
    }
    
    @Test(priority = 3)
    public void testUpdateUser() {
        // Update user data
        userPayload.setName(faker.name().fullName());
        userPayload.setJob(faker.job().position());
        
        // Send PUT request to update user
        Response response = UserEndpoints.updateUser(2, userPayload);
        
        // Print response
        response.then().log().all();
        
        // Validations
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        Assert.assertNotNull(response.jsonPath().getString("updatedAt"), "Updated date should not be null");
        
        System.out.println("✅ User updated successfully!");
    }
    
    @Test(priority = 4)
    public void testDeleteUser() {
        // Send DELETE request
        Response response = UserEndpoints.deleteUser(2);
        
        // Print response
        response.then().log().all();
        
        // Validations
        Assert.assertEquals(response.getStatusCode(), 204, "Status code should be 204");
        
        System.out.println("✅ User deleted successfully!");
    }
    
    @Test(priority = 5)
    public void testGetUserList() {
        // Send GET request to fetch user list
        Response response = UserEndpoints.getUserList(2);
        
        // Print response
        response.then().log().all();
        
        // Validations
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        Assert.assertNotNull(response.jsonPath().getString("data"), "User list should not be null");
        Assert.assertTrue(response.jsonPath().getList("data").size() > 0, "User list should not be empty");
        
        System.out.println("✅ User list fetched successfully!");
    }
}
