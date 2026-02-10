package endpoints;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import payload.User;

/**
 * UserEndpoints.java
 * This class contains all API endpoints for User operations
 * Created for performing CRUD operations on User API
 */
public class UserEndpoints {
    
    /**
     * Create User - POST Request
     */
    public static Response createUser(User payload) {
        Response response = given()
            .contentType("application/json")
            .accept("application/json")
            .body(payload)
        .when()
            .post(Routes.post_url);
        
        return response;
    }
    
    /**
     * Get User - GET Request
     */
    public static Response getUser(int userId) {
        Response response = given()
            .pathParam("id", userId)
            .accept("application/json")
        .when()
            .get(Routes.get_url);
        
        return response;
    }
    
    /**
     * Update User - PUT Request
     */
    public static Response updateUser(int userId, User payload) {
        Response response = given()
            .contentType("application/json")
            .accept("application/json")
            .pathParam("id", userId)
            .body(payload)
        .when()
            .put(Routes.update_url);
        
        return response;
    }
    
    /**
     * Delete User - DELETE Request
     */
    public static Response deleteUser(int userId) {
        Response response = given()
            .pathParam("id", userId)
        .when()
            .delete(Routes.delete_url);
        
        return response;
    }
    
    /**
     * Get List of Users - GET Request
     */
    public static Response getUserList(int page) {
        Response response = given()
            .queryParam("page", page)
            .accept("application/json")
        .when()
            .get(Routes.get_list_url);
        
        return response;
    }
}
