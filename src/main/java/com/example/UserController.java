package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private Database database;

    // Insecure login method - vulnerable to SQL Injection by using concatenated query strings
    @GetMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'"; // Vulnerable to SQL Injection
        try (Connection conn = database.getConnection();
             Statement stmt = conn.createStatement()) {

            try (ResultSet rs = stmt.executeQuery(query)) { // Executes the insecure query
                if (rs.next()) {
                    return "Login successful";
                } else {
                    return "Login failed";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    // Insecure updatePassword method - vulnerable to SQL Injection
    @PutMapping("/update-password")
    public String updatePassword(@RequestParam String username, @RequestParam String oldPassword, @RequestParam String newPassword) {
        String query = "UPDATE users SET password = '" + newPassword + "' WHERE username = '" + username + "' AND password = '" + oldPassword + "'"; // Vulnerable to SQL Injection
        try (Connection conn = database.getConnection();
             Statement stmt = conn.createStatement()) {

            int rowsAffected = stmt.executeUpdate(query); // Executes the insecure update
            return rowsAffected > 0 ? "Password updated" : "Update failed";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error";
        }
    }
}

