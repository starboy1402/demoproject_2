package com.digigov.backend.controller;

import com.digigov.backend.entity.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// @RestController - This tells Spring this class handles HTTP requests and returns JSON responses
// It combines @Controller and @ResponseBody annotations
@RestController

// @RequestMapping - This sets the base URL path for all endpoints in this controller
// All endpoints will start with "/api/users"
@RequestMapping("/api/users")

// @CrossOrigin - This allows your frontend (running on different port) to call these APIs
// Without this, browser would block the requests due to CORS policy
@CrossOrigin(origins = "*")
public class UserController {
    
    // @GetMapping - This handles HTTP GET requests to "/api/users/test"
    // When someone visits http://localhost:8080/api/users/test, this method runs
    @GetMapping("/test")
    public String testEndpoint() {
        // Simple method to test if our API is working
        // Returns plain text response
        return "User API is working!";
    }
    
    // @GetMapping - This handles GET requests to "/api/users" (no additional path)
    // This will be used to fetch all users from database
    @GetMapping
    public List<User> getAllUsers() {
        // For now, we return an empty list since we haven't connected to database yet
        // Later, this will fetch actual users from database
        return new ArrayList<>();
    }
    
    // @PostMapping - This handles HTTP POST requests to "/api/users/create"
    // POST is used when creating new data
    // @RequestBody - This tells Spring to convert the JSON from frontend into a User object
    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        // This method receives a User object from frontend (JSON gets converted automatically)
        
        // For now, we manually set an ID since we're not saving to database yet
        // Later, database will auto-generate the ID
        user.setUserId(1L);
        
        // Return the user object (Spring converts it back to JSON automatically)
        return user;
    }
}