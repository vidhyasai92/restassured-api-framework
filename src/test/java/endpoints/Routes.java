package endpoints;

/**
 * Routes.java
 * Contains all API endpoint URLs
 * Centralized location for all routes
 */
public class Routes {
    
    public static String base_url = "https://jsonplaceholder.typicode.com";
    
    // User module endpoints
    public static String post_url = base_url + "/users";
    public static String get_url = base_url + "/users/{id}";
    public static String update_url = base_url + "/users/{id}";
    public static String delete_url = base_url + "/users/{id}";
    public static String get_list_url = base_url + "/users";
}
